package dev.aisandbox.client.scenarios.twisty;

import dev.aisandbox.client.parameters.BooleanParameter;
import dev.aisandbox.client.parameters.EnumerationParameter;
import dev.aisandbox.client.parameters.LongParameter;
import dev.aisandbox.client.scenarios.BaseScenario;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube10x10x10;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube2x2x2;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube3x3x3;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube4x4x4;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube5x5x5;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube6x6x6;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube7x7x7;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube8x8x8;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube9x9x9;
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
    switch (twistyType.getValue()) {
      case CUBE2:
        runtime.setPuzzle(new Cube2x2x2());
        break;
      case CUBE3:
        runtime.setPuzzle(new Cube3x3x3());
        break;
      case CUBE4:
        runtime.setPuzzle(new Cube4x4x4());
        break;
      case CUBE5:
        runtime.setPuzzle(new Cube5x5x5());
        break;
      case CUBE6:
        runtime.setPuzzle(new Cube6x6x6());
        break;
      case CUBE7:
        runtime.setPuzzle(new Cube7x7x7());
        break;
      case CUBE8:
        runtime.setPuzzle(new Cube8x8x8());
        break;
      case CUBE9:
        runtime.setPuzzle(new Cube9x9x9());
        break;
      case CUBE10:
        runtime.setPuzzle(new Cube10x10x10());
        break;
      default:
        log.error("Unknown puzzle type");
        runtime.setPuzzle(new Cube3x3x3());
    }
    if (scenarioSalt.getValue() != 0) {
      runtime.setRandom(new Random(scenarioSalt.getValue()));
    }
    runtime.setStartSolved(twistyStartSolved.getValue());
    return runtime;
  }
}
