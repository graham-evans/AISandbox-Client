package dev.aisandbox.client.scenarios.mine;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.AgentException;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.output.charts.SuccessRateGraph;
import dev.aisandbox.client.profiler.ProfileStep;
import dev.aisandbox.client.scenarios.maze.MazeRunner;
import dev.aisandbox.client.scenarios.mine.api.LastMove;
import dev.aisandbox.client.scenarios.mine.api.MineHunterRequest;
import dev.aisandbox.client.scenarios.mine.api.MineHunterResponse;
import dev.aisandbox.client.scenarios.mine.api.Move;
import dev.aisandbox.client.sprite.SpriteLoader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.Setter;

public class MineHunterThread extends Thread {

  private static final Logger LOG = Logger.getLogger(MineHunterThread.class.getName());

  private final Agent agent;
  private final FrameOutput output;
  private final GameRunController controller;
  private final Random random;
  private final SizeEnum size;
  private final Long maxStepCount;

  private Board board;
  private double scale = 1.0;

  private BufferedImage logo;

  private List<BufferedImage> sprites;

  SuccessRateGraph winRateGraph = new SuccessRateGraph();
  BufferedImage winRateGraphImage = null;

  Font myFont = new Font("Sans-Serif", Font.PLAIN, 28);

  public MineHunterThread(
      Agent agent,
      FrameOutput output,
      GameRunController controller,
      Random random,
      SpriteLoader loader,
      SizeEnum size,
      Long maxSteps) {
    this.agent = agent;
    this.output = output;
    this.controller = controller;
    this.random = random;
    this.size = size;
    this.maxStepCount = maxSteps;
    // load images
    LOG.info("Loading sprites");
    try {
      logo =
          ImageIO.read(MazeRunner.class.getResourceAsStream("/dev/aisandbox/client/fx/logo1.png"));
    } catch (IOException e) {
      LOG.log(Level.SEVERE, "Error loading logo", e);
      logo = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    }
    sprites = loader.loadSprites("/dev/aisandbox/client/scenarios/mine/grid.png", 40, 40);
  }

  @Getter @Setter private boolean running = false;

  @Override
  public void run() {
    try {
      // setup agent
      agent.setupAgent();
      // setup starting board
      getNewBoard();
      running = true;
      LastMove last = null;
      long stepCount = 0;
      // main loop
      while (running) {
        stepCount++;
        ProfileStep profileStep = new ProfileStep();
        // draw image
        BufferedImage image = createLevelImage();
        controller.updateBoardImage(image);
        output.addFrame(image);
        profileStep.addStep("Graphics");
        // send a request
        MineHunterRequest request = new MineHunterRequest();
        request.setLastMove(last);
        request.setBoardID(board.getBoardID());
        request.setFlagsRemaining(board.getUnfoundMines());
        request.setBoard(board.getBoardToString());
        try {
          MineHunterResponse response = agent.postRequest(request, MineHunterResponse.class);
          profileStep.addStep("Network");
          for (Move move : response.getMoves()) {
            boolean change =
                move.isFlag()
                    ? board.placeFlag(move.getX(), move.getY())
                    : board.uncover(move.getX(), move.getY());
            // if something has changed, redraw the screen
            if (change) {
              image = createLevelImage();
              controller.updateBoardImage(image);
              output.addFrame(image);
            }
            // if the level has ended, dont make any more changes
            if (board.getState() != GameState.PLAYING) {
              break;
            }
          }
          profileStep.addStep("Simulation");
        } catch (AgentException e) {
          controller.showAgentError(agent.getTarget(), e);
          running = false;
        }
        controller.addProfileStep(profileStep);
        // record the result of the last move
        last = new LastMove();
        last.setBoardID(board.getBoardID());
        last.setResult(board.getState().name());
        // if the game has finished create a new one
        if (board.getState() != GameState.PLAYING) {
          winRateGraph.addValue(board.getState() == GameState.WON ? 100.0 : 0.0);
          getNewBoard();
        }
        // if we reach the max step - finish
        if ((maxStepCount != null) && (maxStepCount == stepCount)) {
          running = false;
        }
      }
    } catch (IOException e) {
      LOG.log(Level.SEVERE, "Error running mine thread", e);
    }
    LOG.info("Finished run thread");
    controller.resetStartButton();
  }

  private void getNewBoard() {
    // create a board
    LOG.info("Initialising board");
    switch (size) {
      case SMALL:
        board = new Board(9, 9);
        board.placeMines(random, 10);
        break;
      case MEDIUM:
        board = new Board(16, 16);
        board.placeMines(random, 40);
        break;
      case LARGE:
        board = new Board(24, 24);
        board.placeMines(random, 99);
        break;
      case MEGA:
        board = new Board(40, 40);
        board.placeMines(random, 150);
        break;
    }
    board.countNeighbours();
    scale = 20.0 / board.getHeight();
    LOG.log(Level.INFO, "Scaling board to {0}", new Object[] {scale});
    winRateGraphImage = winRateGraph.getGraph(600, 250);
  }

  private BufferedImage createLevelImage() {
    BufferedImage image = OutputTools.getWhiteScreen();
    Graphics2D g = image.createGraphics();
    // add logo
    g.drawImage(logo, 100, 50, null);
    // draw graphcs
    g.drawImage(winRateGraphImage, 1200, 200, null);
    g.setColor(Color.BLACK);
    g.setFont(myFont);
    g.drawString("Mines Remaining : " + board.getUnfoundMines(), 1200, 500);

    // transform for drawing the board
    g.translate(100, 200);
    g.scale(scale, scale);
    for (int x = 0; x < board.getWidth(); x++) {
      for (int y = 0; y < board.getHeight(); y++) {
        Cell c = board.getCell(x, y);
        switch (c.getPlayerView()) {
          case '#':
            g.drawImage(sprites.get(11), x * 40, y * 40, null);
            break;
          case 'F':
            g.drawImage(sprites.get(12), x * 40, y * 40, null);
            break;
          case 'f':
            g.drawImage(sprites.get(13), x * 40, y * 40, null);
            break;
          case 'X':
            g.drawImage(sprites.get(10), x * 40, y * 40, null);
            break;
          case '.':
            g.drawImage(sprites.get(0), x * 40, y * 40, null);
            break;
          case '1':
            g.drawImage(sprites.get(1), x * 40, y * 40, null);
            break;
          case '2':
            g.drawImage(sprites.get(2), x * 40, y * 40, null);
            break;
          case '3':
            g.drawImage(sprites.get(3), x * 40, y * 40, null);
            break;
          case '4':
            g.drawImage(sprites.get(4), x * 40, y * 40, null);
            break;
          case '5':
            g.drawImage(sprites.get(5), x * 40, y * 40, null);
            break;
          case '6':
            g.drawImage(sprites.get(6), x * 40, y * 40, null);
            break;
          case '7':
            g.drawImage(sprites.get(7), x * 40, y * 40, null);
            break;
          case '8':
            g.drawImage(sprites.get(8), x * 40, y * 40, null);
            break;
          case '9':
            g.drawImage(sprites.get(9), x * 40, y * 40, null);
            break;
          default:
            LOG.warning("Unexpected char");
        }
      }
    }
    return image;
  }

  protected void stopSimulation() {
    running = false;
    try {
      this.join();
    } catch (InterruptedException e) {
      LOG.log(Level.WARNING, "Interrupted!", e);
      // Restore interrupted state...
      Thread.currentThread().interrupt();
    }
  }
}
