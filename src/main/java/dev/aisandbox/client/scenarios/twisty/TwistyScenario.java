package dev.aisandbox.client.scenarios.twisty;

import dev.aisandbox.client.parameters.BooleanParameter;
import dev.aisandbox.client.parameters.LongParameter;
import dev.aisandbox.client.parameters.OptionParameter;
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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * TwistyScenario class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
@Data
@Slf4j
public class TwistyScenario implements Scenario {

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

  private OptionParameter twistyType = new OptionParameter("twisty.type", PUZZLE_TYPES);
  private LongParameter scenarioSalt = new LongParameter("twisty.salt", 0, "Random Salt", null);
  private BooleanParameter twistyStartSolved =
      new BooleanParameter("twisty.solved", false, "Start Solved", null);

  /** {@inheritDoc} */
  @Override
  public ScenarioType getGroup() {
    return ScenarioType.INTERMEDIATE;
  }

  @Override
  public String getId() {
    return "twisty";
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return "Twisty Puzzle";
  }

  /** {@inheritDoc} */
  @Override
  public String getOverview() {
    return "Puzzles based on rotating layers and circles";
  }

  /** {@inheritDoc} */
  @Override
  public String getDescription() {
    return "Puzzles based on rotating layers and circles";
  }

  /** {@inheritDoc} */
  @Override
  public String getImageReference() {
    return "/dev/aisandbox/client/scenarios/twisty/sample.png";
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
  public String getScenarioURL() {
    return "https://aisandbox.dev/scenarios-twisty/";
  }

  /** {@inheritDoc} */
  @Override
  public String getSwaggerURL() {
    return "https://files.aisandbox.dev/swagger/twisty.yaml";
  }

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[] {scenarioSalt, twistyType, twistyStartSolved};
  }

  @Override
  public ScenarioRuntime getRuntime() {
    TwistyRuntime runtime = new TwistyRuntime();
    switch (twistyType.getOptionIndex()) {
      case 0:
        runtime.setPuzzle(new Cube2x2x2());
        break;
      case 1:
        runtime.setPuzzle(new Cube3x3x3());
        break;
      case 2:
        runtime.setPuzzle(new Cube4x4x4());
        break;
      case 3:
        runtime.setPuzzle(new Cube5x5x5());
        break;
      case 4:
        runtime.setPuzzle(new Cube6x6x6());
        break;
      case 5:
        runtime.setPuzzle(new Cube7x7x7());
        break;
      case 6:
        runtime.setPuzzle(new Cube8x8x8());
        break;
      case 7:
        runtime.setPuzzle(new Cube9x9x9());
        break;
      case 8:
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
