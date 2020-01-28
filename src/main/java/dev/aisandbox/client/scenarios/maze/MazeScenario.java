package dev.aisandbox.client.scenarios.maze;

import com.dooapp.fxform.annotation.NonVisual;
import dev.aisandbox.client.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioType;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MazeScenario class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
public class MazeScenario implements Scenario {

  @NonVisual
  private static final Logger LOG = LoggerFactory.getLogger(MazeScenario.class.getName());

  @NonVisual private MazeRunner runner = null;

  @NonVisual @Autowired private MazeRenderer renderer;

  // configurable properties
  @Getter @Setter private Long scenarioSalt = 0l;
  @Getter @Setter private MazeType mazeType = MazeType.BINARYTREE;
  @Getter @Setter private MazeSize mazeSize = MazeSize.MEDIUM;

  @Override
  public ScenarioType getGroup() {
    return ScenarioType.INTRODUCTION;
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return "Maze Runner";
  }

  /** {@inheritDoc} */
  @Override
  public String getOverview() {
    return "Navigate the maze and find the exit, then optimise the path to find the shortest route.";
  }

  /** {@inheritDoc} */
  @Override
  public String getDescription() {
    return "The AI agent will be placed in a Maze and tasked with finding its way to the exit. Once there it will be rewarded and returned to the beginning.\n"
        + "At each turn the AI agent is given information about the maze (dimensions, directions etc), the result of the last move (any reward) and asked for the next move. This repeats until the scenario is manually stopped.";
  }

  /** {@inheritDoc} */
  @Override
  public int getMinAgentCount() {
    return 1;
  }

  /** {@inheritDoc} */
  @Override
  public int getMaxAgentCount() {
    return 1;
  }

  @Override
  public String getImageReference() {
    return "/dev/aisandbox/client/scenarios/maze/sample.png";
  }

  @Override
  public String getScenarioURL() {
    return "https://aisandbox.dev/scenarios-maze/";
  }

  @Override
  public String getSwaggerURL() {
    return "https://files.aisandbox.dev/swagger/maze.yaml";
  }

  /** {@inheritDoc} */
  @Override
  public void startSimulation(
      List<Agent> agentList, GameRunController ui, FrameOutput output, Long stepCount) {
    LOG.info("Salt {}", scenarioSalt);
    LOG.info("Generating maze");
    Maze maze;
    switch (mazeSize) {
      case SMALL:
        maze = new Maze(8, 6);
        maze.setZoomLevel(5);
        break;
      case MEDIUM:
        maze = new Maze(20, 15);
        maze.setZoomLevel(2);
        break;
      default: // LARGE
        maze = new Maze(40, 30);
        maze.setZoomLevel(1);
    }
    Random rand;
    if (scenarioSalt == 0) {
      rand = new Random();
    } else {
      rand = new Random(scenarioSalt);
    }
    switch (mazeType) {
      case BINARYTREE:
        MazeUtilities.applyBinaryTree(rand, maze);
        break;
      case SIDEWINDER:
        MazeUtilities.applySidewinder(rand, maze);
        break;
      case RECURSIVEBACKTRACKER:
        MazeUtilities.applyRecursiveBacktracker(rand, maze);
        break;
      case BRAIDED:
        MazeUtilities.applyRecursiveBacktracker(rand, maze);
        MazeUtilities.removeDeadEnds(rand, maze);
        break;
    }
    MazeUtilities.findFurthestPoints(maze);
    // render base map
    BufferedImage image = renderer.renderMaze(maze);
    runner = new MazeRunner(agentList.get(0), maze, output, ui, image, stepCount);
    LOG.info("Starting simulation");
    runner.start();
  }

  /** {@inheritDoc} */
  @Override
  public void stopSimulation() {
    LOG.info("Stopping simulation");
    if (runner != null) {
      runner.stopSimulation();
      runner = null;
    }
  }

  @Override
  public void joinSimulation() {
    LOG.info("Joining simulation");
    if (runner != null) {
      try {
        runner.join();
      } catch (InterruptedException e) {
        LOG.warn("Interrupted!", e);
        // Restore interrupted state...
        Thread.currentThread().interrupt();
      }
      runner = null;
    }
  }

  /**
   * Tell if the simulation is currently running
   *
   * @return True if the simulation is running.
   */
  @Override
  public boolean isSimulationRunning() {
    if (runner == null) {
      return false;
    }
    return runner.isRunning();
  }
}
