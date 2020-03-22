package dev.aisandbox.client.scenarios.twisty;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.scenarios.maze.MazeRunner;
import dev.aisandbox.client.scenarios.twisty.api.TwistyRequest;
import dev.aisandbox.client.scenarios.twisty.api.TwistyResponse;
import dev.aisandbox.client.scenarios.twisty.model.Cell;
import dev.aisandbox.client.scenarios.twisty.model.Move;
import dev.aisandbox.client.scenarios.twisty.model.TwistyPuzzle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwistyThread extends Thread {

  private final Agent agent;
  private final FrameOutput output;
  private final GameRunController controller;
  private final Random random;
  private final PuzzleType puzzleType;
  private final Long maxStepCount;
  private BufferedImage logo;
  private Map<Character, Color> faceColourCache = new HashMap<>();
  private static final int SCRAMBLE_MOVES = 200;

  @Getter private boolean running = false;

  public TwistyThread(
      Agent agent,
      FrameOutput output,
      GameRunController controller,
      Random random,
      PuzzleType puzzleType,
      Long maxStepCount) {
    this.agent = agent;
    this.output = output;
    this.controller = controller;
    this.random = random;
    this.puzzleType = puzzleType;
    this.maxStepCount = maxStepCount;
    try {
      logo =
          ImageIO.read(MazeRunner.class.getResourceAsStream("/dev/aisandbox/client/fx/logo1.png"));
    } catch (IOException e) {
      log.error("Error loading logo", e);
    }
  }

  @Override
  public void run() {
    running = true;
    try {
      // setup agent
      agent.setupAgent();
      // setup puzzle
      TwistyPuzzle twistyPuzzle = PuzzleLoader.loadPuzzle(puzzleType);
      // cache the face colours
      twistyPuzzle
          .getFaces()
          .forEach(
              (c, f) -> {
                faceColourCache.put(c, Color.decode(f.getBaseColour()));
              });
      log.debug("Found {} face colours ({})", faceColourCache.size(), faceColourCache);
      // cache the list of moves
      List<String> moveNames = new ArrayList<>();
      moveNames.addAll(twistyPuzzle.getMoves().keySet());
      log.debug("Found {} moves ({})", moveNames.size(), moveNames);
      // reset the puzzle
      resetPuzzle(twistyPuzzle);
      // scramble the puzzle
      for (int i = 0; i < SCRAMBLE_MOVES; i++) {
        String randomMove = moveNames.get(random.nextInt(moveNames.size()));
        applyMove(twistyPuzzle, randomMove);
      }
      // save the cell colours - just in case we want to reset the puzzle
      Map<String, Character> savedColours = new HashMap<>();
      twistyPuzzle
          .getCells()
          .forEach((name, cell) -> savedColours.put(name, cell.getCurrentColour()));
      // create move list
      List<String> actions = new ArrayList<>();
      // draw current state
      BufferedImage image = renderPuzzle(twistyPuzzle);
      controller.updateBoardImage(image);
      output.addFrame(image);
      while (running) {
        if (actions.isEmpty()) {
          // get next set of actions
          TwistyRequest request = new TwistyRequest();
          request.setPuzzleType(puzzleType.toString());
          request.setMoves(moveNames);
          request.setState(twistyPuzzle.getState());
          // TODO - include history
          TwistyResponse response = agent.postRequest(request, TwistyResponse.class);
          actions.addAll(Arrays.asList(response.getMove().split(" ")));
        }
        // perform actions
        if (!actions.isEmpty()) {
          String action = actions.remove(0);
          applyMove(twistyPuzzle, action);
          // draw current state
          image = renderPuzzle(twistyPuzzle);
          controller.updateBoardImage(image);
          output.addFrame(image);
        }

        // check for completeness
        // running = false;
      }
    } catch (AgentException ae) {
      controller.showAgentError(agent.getTarget(), ae);
    } catch (NotExistentMoveException e) {
      log.error("Tried to use a non existing move", e);
    } catch (IOException e) {
      log.error("IO Error running simulation", e);
    }
    running = false;
    controller.resetStartButton();
  }

  protected void stopSimulation() {
    running = false;
    try {
      this.join();
    } catch (InterruptedException e) {
      log.warn("Interrupted!", e);
      // Restore interrupted state...
      Thread.currentThread().interrupt();
    }
  }

  protected void applyMove(TwistyPuzzle puzzle, String move) throws NotExistentMoveException {
    log.info("Applying move {}", move);
    Move m = puzzle.getMoves().get(move);
    if (m == null) {
      throw new NotExistentMoveException("Move '" + move + "' isn't defined");
    }
    m.getCellMapping()
        .forEach(
            (target, src) -> {
              log.debug("Copying {} from {}", target, src);
              Cell sourceCell = puzzle.getCells().get(src);
              Cell targetCell = puzzle.getCells().get(target);
              targetCell.setNextColour(sourceCell.getCurrentColour());
            });
    // bake move
    for (Cell cell : puzzle.getCells().values()) {
      if (cell.getNextColour() != null) {
        cell.setCurrentColour(cell.getNextColour());
        cell.setNextColour(null);
      }
    }
  }

  /**
   * reset each cells current colour to the colour of the face it's on.
   *
   * @param puzzle the puzzle object to reset
   */
  private void resetPuzzle(TwistyPuzzle puzzle) {
    log.info("Resetting puzzle");
    puzzle
        .getFaces()
        .forEach(
            (c, f) -> {
              for (String cellID : f.getCells()) {
                Cell cell = puzzle.getCells().get(cellID);
                cell.setCurrentColour(c);
              }
            });
  }

  private BufferedImage renderPuzzle(TwistyPuzzle puzzle) {
    BufferedImage image = OutputTools.getWhiteScreen();
    Graphics2D g = image.createGraphics();
    // add logo
    g.drawImage(logo, 100, 50, null);
    for (Cell cell : puzzle.getCells().values()) {
      Color colour = faceColourCache.get(cell.getCurrentColour());
      Polygon polygon = cell.getPolygon();
      g.setColor(colour);
      g.fillPolygon(polygon);
      g.setColor(Color.LIGHT_GRAY);
      g.drawPolygon(polygon);
    }
    return image;
  }
}
