package dev.aisandbox.client.scenarios.twisty;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.charts.BaseChart;
import dev.aisandbox.client.output.charts.FrequencyMassDistributionGraph;
import dev.aisandbox.client.profiler.ProfileStep;
import dev.aisandbox.client.scenarios.maze.MazeRunner;
import dev.aisandbox.client.scenarios.twisty.api.TwistyRequest;
import dev.aisandbox.client.scenarios.twisty.api.TwistyRequestHistory;
import dev.aisandbox.client.scenarios.twisty.api.TwistyResponse;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube10x10x10;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube2x2x2;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube3x3x3;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube4x4x4;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube5x5x5;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube6x6x6;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube7x7x7;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube8x8x8;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube9x9x9;
import dev.aisandbox.client.scenarios.twisty.puzzles.CubePuzzle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * TwistyThread class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
public class TwistyThread extends Thread {

  private final Agent agent;
  private final FrameOutput output;
  private final GameRunController controller;
  private final Random random;
  private final PuzzleType puzzleType;
  private final Long maxStepCount;
  private final boolean startSolved;
  private BufferedImage logo;
  private static final int SCRAMBLE_MOVES = 200;

  private List<String> moveHistory = new ArrayList<>();
  private static final int HISTORY_WIDTH = 9;
  private static final int HISTORY_HEIGHT = 5;
  private static final int MOVE_HISTORY_MAX = HISTORY_WIDTH * HISTORY_HEIGHT;
  private FrequencyMassDistributionGraph frequencyGraph = new FrequencyMassDistributionGraph();

  @Getter private boolean running = false;

  private TwistyPuzzle createPuzzle(PuzzleType t) {
    switch (t) {
      case CUBE2:
        return new Cube2x2x2();
      case CUBE3:
        return new Cube3x3x3();
      case CUBE4:
        return new Cube4x4x4();
      case CUBE5:
        return new Cube5x5x5();
      case CUBE6:
        return new Cube6x6x6();
      case CUBE7:
        return new Cube7x7x7();
      case CUBE8:
        return new Cube8x8x8();
      case CUBE9:
        return new Cube9x9x9();
      case CUBE10:
        return new Cube10x10x10();
      default:
        log.error("Unknown puzzle type {}", t);
        return new Cube3x3x3();
    }
  }

  /**
   * Constructor for TwistyThread.
   *
   * @param agent a {@link dev.aisandbox.client.agent.Agent} object.
   * @param output a {@link dev.aisandbox.client.output.FrameOutput} object.
   * @param controller a {@link dev.aisandbox.client.fx.GameRunController} object.
   * @param random a {@link java.util.Random} object.
   * @param puzzleType a {@link dev.aisandbox.client.scenarios.twisty.PuzzleType} object.
   * @param maxStepCount a {@link java.lang.Long} object.
   */
  public TwistyThread(
      Agent agent,
      FrameOutput output,
      GameRunController controller,
      Random random,
      PuzzleType puzzleType,
      Long maxStepCount,
      boolean startSolved) {
    this.agent = agent;
    this.output = output;
    this.controller = controller;
    this.random = random;
    this.puzzleType = puzzleType;
    this.maxStepCount = maxStepCount;
    this.startSolved = startSolved;
    try {
      logo =
          ImageIO.read(MazeRunner.class.getResourceAsStream("/dev/aisandbox/client/fx/logo1.png"));
    } catch (IOException e) {
      log.error("Error loading logo", e);
    }
    // setup graph
    frequencyGraph.setTitle("# Moves to solve");
    frequencyGraph.setXaxisHeader("# Moves");
    frequencyGraph.setYaxisHeader("Frequency");
    frequencyGraph.setGraphWidth(HISTORY_WIDTH * CubePuzzle.MOVE_ICON_WIDTH);
    frequencyGraph.setGraphHeight(350);
  }

  private void scramblePuzzle(TwistyPuzzle twistyPuzzle) throws NotExistentMoveException {
    for (int i = 0; i < SCRAMBLE_MOVES; i++) {
      String randomMove =
          twistyPuzzle.getMoveList().get(random.nextInt(twistyPuzzle.getMoveList().size()));
      twistyPuzzle.applyMove(randomMove);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void run() {
    running = true;
    // setup puzzle
    TwistyPuzzle twistyPuzzle = createPuzzle(puzzleType);
    try {
      // setup agent
      agent.setupAgent();
      // scramble the puzzle
      if (!startSolved) {
        scramblePuzzle(twistyPuzzle);
      }
      // save the cell colours - just in case we want to reset the puzzle
      String savedPuzzle = twistyPuzzle.getState();
      // create an (empty) list of actions
      List<String> actions = new ArrayList<>();
      // count the number of moves made
      int moves = 0;
      // draw current state
      BufferedImage image = renderPuzzle(twistyPuzzle);
      controller.updateBoardImage(image);
      output.addFrame(image);
      // remember history
      TwistyRequestHistory history = null;
      while (running) {
        // keep timings
        ProfileStep profileStep = new ProfileStep();
        if (actions.isEmpty()) {
          // get next set of actions
          TwistyRequest request = new TwistyRequest();
          request.setPuzzleType(puzzleType.toString());
          request.setMoves(twistyPuzzle.getMoveList());
          request.setState(twistyPuzzle.getState());
          request.setHistory(history);
          log.info("Requesting new actions from state {}", twistyPuzzle.getState());
          TwistyResponse response = agent.postRequest(request, TwistyResponse.class);
          actions.addAll(Arrays.asList(response.getMove().trim().split(" ")));
          log.info("Action list now '{}'", actions);
          profileStep.addStep("Network");
          // TODO - Implement "reset puzzle request"
        }
        // perform actions
        if (!actions.isEmpty()) {
          history = new TwistyRequestHistory();
          history.setStartState(twistyPuzzle.getState());
          String action = actions.remove(0);
          history.setMoves(action);
          log.info("Applying move '{}'", action);
          moves += twistyPuzzle.applyMove(action);
          moveHistory.add(action);
          while (moveHistory.size() > MOVE_HISTORY_MAX) {
            moveHistory.remove(0);
          }
          history.setEndState(twistyPuzzle.getState());
          history.setSuccess(twistyPuzzle.isSolved());
          log.info("State now {}", twistyPuzzle.getState());
          profileStep.addStep("Simulation");
          // draw current state
          image = renderPuzzle(twistyPuzzle);
          controller.updateBoardImage(image);
          output.addFrame(image);
          profileStep.addStep("Graphics");
        }
        if (twistyPuzzle.isSolved() && !startSolved) {
          log.info("Puzzle solved, resetting");
          // register solve
          frequencyGraph.addValue(moves);
          frequencyGraph.resetGraph();
          // clear history
          moves = 0;
          moveHistory.clear();
          // scramble
          scramblePuzzle(twistyPuzzle);
          savedPuzzle = twistyPuzzle.getState();
          // draw new state
          image = renderPuzzle(twistyPuzzle);
          controller.updateBoardImage(image);
          output.addFrame(image);
          profileStep.addStep("Puzzle Setup");
        }
        // report profile information
        controller.addProfileStep(profileStep);
      }
    } catch (AgentException ae) {
      controller.showAgentError(agent.getTarget(), ae);
    } catch (NotExistentMoveException e) {
      log.error("Tried to use a non existing move", e);
    } catch (IOException e) {
      log.error("IO Error running simulation", e);
    }
    // update the board just in case the screen isn't up-to-date
    controller.forceUpdateBoardImage(renderPuzzle(twistyPuzzle));
    // stop the recording
    try {
      output.close();
    } catch (IOException e) {
      log.warn("Error closing output", e);
    }
    running = false;
    controller.resetStartButton();
  }

  /** stopSimulation. */
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

  private BufferedImage renderPuzzle(TwistyPuzzle puzzle) {
    BufferedImage image = puzzle.getStateImage();
    Graphics2D g = image.createGraphics();
    g.setFont(new Font("Helvetica", Font.PLAIN, 18));
    g.setRenderingHint(
        RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
    // add logo
    g.drawImage(logo, 100, 50, null);
    // draw history
    for (int i = 0; i < moveHistory.size(); i++) {
      BufferedImage moveImage = puzzle.getMoveImage(moveHistory.get(i));
      if (moveImage != null) {
        g.drawImage(
            moveImage,
            (i % HISTORY_WIDTH) * CubePuzzle.MOVE_ICON_WIDTH + 1350,
            (i / HISTORY_WIDTH) * CubePuzzle.MOVE_ICON_HEIGHT + 550,
            null);
      }
    }
    g.setColor(Color.BLACK);
    if (frequencyGraph.getImage() != null) {
      g.drawImage(frequencyGraph.getImage(), 1350, 100, null);
      g.drawString(
          "Mean : " + BaseChart.toSignificantDigitString(frequencyGraph.getMean(), 5), 1400, 480);
      g.drawString(
          "\u03C3\u00B2 : "
              + BaseChart.toSignificantDigitString(frequencyGraph.getStandardDeviation(), 5),
          1400,
          480 + 24);
    }
    return image;
  }
}
