package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.parameters.LongParameter;
import dev.aisandbox.client.parameters.OptionParameter;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final MazeRenderer mazeRenderer;

  @Autowired
  public MazeScenario(MazeRenderer mazeRenderer) {
    this.mazeRenderer = mazeRenderer;
  }

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

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[] {scenarioSalt, mazeType, mazeSize};
  }

  @Override
  public ScenarioRuntime getRuntime() {
    MazeRuntime runtime = new MazeRuntime(mazeRenderer);
    if (scenarioSalt.getValue() != 0) {
      runtime.setRandom(new Random(scenarioSalt.getValue()));
    }
    runtime.setMazeSize(mazeSize.getOptionIndex());
    runtime.setMazeType(mazeType.getOptionIndex());
    return runtime;
  }
}
