package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import dev.aisandbox.client.scenarios.parameters.LongParameter;
import dev.aisandbox.client.scenarios.parameters.OptionParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * MazeScenario class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
@Slf4j
public class MazeScenario implements Scenario {

  // configurable properties
  private LongParameter scenarioSalt =
      new LongParameter("maze.salt", 0, "Random Salt", "Set this to zero for a random maze.");
  private OptionParameter mazeType =
      new OptionParameter(
          "maze.type",
          new String[] {
            "Binary Tree (Biased)",
            "Sidewinder",
            "Recursive Backtracker",
            "Braided (includes loops)"
          },
          "Maze Type",
          null);
  private OptionParameter mazeSize =
      new OptionParameter(
          "maze.size",
          new String[] {"Small (8x6)", "Medium (20x15)", "Large (40x30)"},
          "Maze Size",
          null);

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
  @Override
  public String getImageReference() {
    return "/dev/aisandbox/client/scenarios/maze/sample.png";
  }

  /** {@inheritDoc} */
  @Override
  public String getScenarioURL() {
    return "https://aisandbox.dev/scenarios-maze/";
  }

  /** {@inheritDoc} */
  @Override
  public String getSwaggerURL() {
    return "https://files.aisandbox.dev/swagger/maze.yaml";
  }

  //  public void startSimulation(
  //      List<Agent> agentList, GameRunController ui, FrameOutput output, Long stepCount) {
  //    log.info("Salt {}", scenarioSalt);
  //    log.info("Generating maze");
  //    Maze maze;
  //    switch (mazeSize) {
  //      case SMALL:
  //        maze = new Maze(8, 6);
  //        maze.setZoomLevel(5);
  //        break;
  //      case MEDIUM:
  //        maze = new Maze(20, 15);
  //        maze.setZoomLevel(2);
  //        break;
  //      default: // LARGE
  //        maze = new Maze(40, 30);
  //        maze.setZoomLevel(1);
  //    }
  //    Random rand;
  //    if (scenarioSalt == 0) {
  //      rand = new Random();
  //    } else {
  //      rand = new Random(scenarioSalt);
  //    }
  //    switch (mazeType) {
  //      case BINARYTREE:
  //        MazeUtilities.applyBinaryTree(rand, maze);
  //        break;
  //      case SIDEWINDER:
  //        MazeUtilities.applySidewinder(rand, maze);
  //        break;
  //      case RECURSIVEBACKTRACKER:
  //        MazeUtilities.applyRecursiveBacktracker(rand, maze);
  //        break;
  //      case BRAIDED:
  //        MazeUtilities.applyRecursiveBacktracker(rand, maze);
  //        MazeUtilities.removeDeadEnds(rand, maze);
  //        break;
  //    }
  //    MazeUtilities.findFurthestPoints(maze);
  //    // render base map
  //    BufferedImage image = renderer.renderMaze(maze);
  //    runner = new MazeRunner(agentList.get(0), maze, output, ui, image, stepCount);
  //    log.info("Starting simulation");
  //    runner.start();
  //  }

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[] {scenarioSalt, mazeType, mazeSize};
  }

  @Override
  public ScenarioRuntime getRuntime() {
    return null;
  }
}
