package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.AgentException;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.output.charts.LineGraph;
import dev.aisandbox.client.profiler.ProfileStep;
import dev.aisandbox.client.scenarios.maze.api.History;
import dev.aisandbox.client.scenarios.maze.api.MazeRequest;
import dev.aisandbox.client.scenarios.maze.api.MazeResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MazeRunner class.
 *
 * @author gde
 * @version $Id: $Id
 */
@RequiredArgsConstructor
public class MazeRunner extends Thread {

  private static final Logger LOG = LoggerFactory.getLogger(MazeRunner.class.getName());
  private static final double REWARD_STEP = -1.0;
  private static final double REWARD_HIT_WALL = -1000.0;
  private static final double REWARD_GOAL = +1000.0;
  private final Agent agent;
  private final Maze maze;
  private final FrameOutput output;
  private final GameRunController controller;
  private final BufferedImage background;
  private final Long maxStepCount;
  private LineGraph graph;
  private BufferedImage graphCache;
  private static final int GRAPH_WIDTH = 600;
  private static final int GRAPH_HEIGHT = 250;
  @Getter private boolean running = false;

  /** {@inheritDoc} */
  @Override
  public void run() {
    // setup data structures
    History lastMove = null;
    Cell currentCell = maze.getStartCell();
    // setup agent
    agent.setupAgent();
    // load graphics
    BufferedImage logo;
    try {
      logo =
          ImageIO.read(MazeRunner.class.getResourceAsStream("/dev/aisandbox/client/fx/logo1.png"));
    } catch (IOException e) {
      LOG.error("Error loading logo", e);
      logo = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    }
    Font myFont = new Font("Sans-Serif", Font.PLAIN, 28);
    graph = new LineGraph();
    graph.setTitle("Steps to solve");
    graphCache = graph.getGraph(GRAPH_WIDTH, GRAPH_HEIGHT);
    // main game loop
    running = true;
    long stepCount = 0;
    long stepToFinish = 0;
    Long fastestSolve = null;
    while (running) {
      // keep timings
      ProfileStep profileStep = new ProfileStep();
      // work out postRequest
      MazeRequest request = new MazeRequest();
      // populate the config
      request.setConfig(maze.getConfig());
      if (lastMove != null) {
        request.setHistory(lastMove);
      }
      request.setCurrentPosition(currentCell.getPosition());
      profileStep.addStep("Setup");
      // send and get response
      try {
        stepCount++;
        stepToFinish++;
        MazeResponse response = agent.postRequest(request, MazeResponse.class);
        LOG.info("Recieved response from server - {}", response);
        profileStep.addStep("Network");
        lastMove = new History();
        lastMove.setLastPosition(currentCell.getPosition());
        lastMove.setAction(response.getMove());
        // move the agent
        Direction direction = Direction.valueOf(response.getMove().toUpperCase());
        if (currentCell.getPaths().contains(direction)) {
          // move to the new cell
          currentCell = currentCell.getNeighbours().get(direction);
          lastMove.setReward(REWARD_STEP);
        } else {
          // hit wall
          lastMove.setReward(REWARD_HIT_WALL);
        }
        // special case - have we found the end?
        if (currentCell.equals(maze.getEndCell())) {
          lastMove.setReward(REWARD_GOAL);
          currentCell = maze.getStartCell();
          graph.addValue((double) stepToFinish);
          graphCache = graph.getGraph(GRAPH_WIDTH, GRAPH_HEIGHT);
          if ((fastestSolve == null) || (fastestSolve > stepToFinish)) {
            fastestSolve = stepToFinish;
          }
          stepToFinish = 0;
        }
        LOG.info("Moved to {}", currentCell);
        lastMove.setNewPosition(currentCell.getPosition());
        profileStep.addStep("Simulation");
        // redraw the map
        BufferedImage image = OutputTools.getWhiteScreen();
        Graphics2D g = image.createGraphics();
        g.setFont(myFont);
        // maze
        g.drawImage(background, 100, 200, 1000, 750, null);
        // player
        g.setColor(Color.yellow);
        g.fillOval(
            currentCell.getPositionX() * MazeRenderer.SCALE * maze.getZoomLevel() + 100,
            200 + currentCell.getPositionY() * MazeRenderer.SCALE * maze.getZoomLevel(),
            MazeRenderer.SCALE * maze.getZoomLevel(),
            MazeRenderer.SCALE * maze.getZoomLevel());
        // logo
        g.drawImage(logo, 100, 50, null);
        // state
        g.setColor(Color.BLACK);
        g.drawString("Steps : " + stepToFinish, 1200, 500);
        g.drawString("Total Steps : " + stepCount, 1200, 500 + 30);
        g.drawString(
            "Fastest Solve: " + (fastestSolve == null ? "NA" : fastestSolve), 1200, 500 + 60);
        // graph
        g.drawImage(graphCache, 1200, 200, null);
        // update UI
        controller.updateBoardImage(image);
        // output frame
        output.addFrame(image);
        profileStep.addStep("Graphics");
        // report profine information
        controller.addProfileStep(profileStep);
      } catch (AgentException ae) {
        controller.showAgentError(agent.getTarget(), ae);
        running = false;
      } catch (Exception ex) {
        LOG.error("Error running", ex);
        running = false;
      }
      // check for step count
      if ((maxStepCount != null) && (maxStepCount == stepCount)) {
        LOG.info("Finishing simulation, max steps reached");
        running = false;
      }
    }
    try {
      output.close();
    } catch (IOException e) {
      LOG.warn("Error closing output", e);
    }
    LOG.info("Finished run thread");
    controller.resetStartButton();
  }

  /** stopSimulation. */
  public void stopSimulation() {
    running = false;
    try {
      this.join();
    } catch (InterruptedException e) {
      LOG.warn("Interrupted!", e);
      // Restore interrupted state...
      Thread.currentThread().interrupt();
    }
  }
}
