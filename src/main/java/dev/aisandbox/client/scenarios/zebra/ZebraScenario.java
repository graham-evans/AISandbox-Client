package dev.aisandbox.client.scenarios.zebra;

import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

// @Component
/**
 * ZebraScenario class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
public class ZebraScenario implements Scenario {

  // configuration
  @Getter @Setter private Long scenarioSalt = 0l;

  @Getter @Setter
  private ZebraPuzzleDifficultyEnum zebraDifficulty = ZebraPuzzleDifficultyEnum.MEDIUM;

  @Getter @Setter private boolean zebraMultipleGuesses = true;

  /** {@inheritDoc} */
  @Override
  public ScenarioType getGroup() {
    return ScenarioType.TEXT;
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return "Zebra Puzzle";
  }

  /** {@inheritDoc} */
  @Override
  public String getOverview() {
    return "Text parsing and constraint solving puzzles, based on the classic Zebra Puzzle.";
  }

  /** {@inheritDoc} */
  @Override
  public String getDescription() {
    return "Long description";
  }

  /** {@inheritDoc} */
  @Override
  public String getImageReference() {
    return "/dev/aisandbox/client/scenarios/zebra/sample.png";
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
    return "https://aisandbox.dev/scenarios-zebra/";
  }

  /** {@inheritDoc} */
  @Override
  public String getSwaggerURL() {
    return "https://files.aisandbox.dev/swagger/zebra.yaml";
  }

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[0];
  }

  @Override
  public ScenarioRuntime getRuntime() {
    return null;
  }
}
