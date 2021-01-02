package dev.aisandbox.client.scenarios.twisty;

import dev.aisandbox.client.parameters.BooleanParameter;
import dev.aisandbox.client.parameters.EnumerationParameter;
import dev.aisandbox.client.parameters.LongParameter;
import dev.aisandbox.client.scenarios.BaseScenario;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * TwistyScenario class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
@Slf4j
public class TwistyScenario extends BaseScenario implements Scenario {

  /** Constructor for the Twisty puzzles. */
  public TwistyScenario() {
    super(
        "twisty",
        "Twisty Puzzle",
        ScenarioType.INTERMEDIATE,
        "Puzzles based on rotating layers and circles",
        "Puzzles based on rotating layers and circles",
        "/dev/aisandbox/client/scenarios/twisty/sample.png",
        1,
        1,
        "https://aisandbox.dev/scenarios-twisty/",
        "https://files.aisandbox.dev/swagger/twisty.yaml");
  }

  private static final String[] PUZZLE_TYPES =
      new String[] {
        "Cube 2x2x2 (OBTM)",
        "Cube 3x3x3 (OBTM)",
        "Cube 4x4x4 (OBTM)",
        "Cube 5x5x5 (OBTM)",
        "Cube 6x6x6 (OBTM)",
        "Cube 7x7x7 (OBTM)",
        "Cube 8x8x8 (OBTM)",
        "Cube 9x9x9 (OBTM)",
        "Cube 10x10x10 (OBTM)"
      };

  private EnumerationParameter<PuzzleType> twistyType =
      new EnumerationParameter<>(
          "twisty.type",
          PuzzleType.CUBE3,
          "Type of puzzle",
          "Select the type of puzzle to generate");
  private LongParameter scenarioSalt =
      new LongParameter("twisty.salt", 0, "Random Salt", "Set this to zero for a random start.");
  private BooleanParameter twistyStartSolved =
      new BooleanParameter(
          "twisty.solved",
          false,
          "Start Solved",
          "If true the puzzle will start in its solved state");

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[] {scenarioSalt, twistyType, twistyStartSolved};
  }

  @Override
  public ScenarioRuntime getRuntime() {
    TwistyRuntime runtime = new TwistyRuntime();
    runtime.setPuzzle(
        new TPPuzzle(twistyType.getValue().getResource(), twistyType.getValue().getID()));
    if (scenarioSalt.getValue() != 0) {
      runtime.setRandom(new Random(scenarioSalt.getValue()));
    }
    runtime.setStartSolved(twistyStartSolved.getValue());
    return runtime;
  }
}
