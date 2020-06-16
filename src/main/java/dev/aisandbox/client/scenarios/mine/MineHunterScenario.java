package dev.aisandbox.client.scenarios.mine;

import dev.aisandbox.client.parameters.LongParameter;
import dev.aisandbox.client.parameters.OptionParameter;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import dev.aisandbox.client.sprite.SpriteLoader;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MineHunterScenario class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
@Slf4j
public class MineHunterScenario implements Scenario {

  // configuration
  private LongParameter scenarioSalt =
      new LongParameter("mine.salt", 0, "Random Salt", "Set this to zero for a random maze.");
  private OptionParameter mineHunterBoardSize =
      new OptionParameter(
          "mine.size",
          new String[] {
            "Small (8x6 10 Mines)",
            "Medium (16x16 40 Mines)",
            "Large (24x24 99 Mines)",
            "Mega (40x40 150 Mines)"
          },
          "Board size",
          null);
  private final SpriteLoader spriteLoader;

  @Autowired
  public MineHunterScenario(SpriteLoader spriteLoader) {
    this.spriteLoader = spriteLoader;
  }

  @Override
  public String getId() {
    return "mine";
  }

  /** {@inheritDoc} */
  @Override
  public ScenarioType getGroup() {
    return ScenarioType.INTRODUCTION;
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return "Mine Hunter";
  }

  /** {@inheritDoc} */
  @Override
  public String getOverview() {
    return "Find the mines in a grid using deduction";
  }

  /** {@inheritDoc} */
  @Override
  public String getDescription() {
    return "Mine Hunter pits the AI against a minefield! A known quantity of mines has been distributed across a grid of squares and the AI agent must work out where they are. To help, each uncovered square will show how many mines are in the surrounding squares.";
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
    return "/dev/aisandbox/client/scenarios/mine/sample.png";
  }

  /** {@inheritDoc} */
  @Override
  public String getScenarioURL() {
    return "https://aisandbox.dev/scenarios-mine/";
  }

  /** {@inheritDoc} */
  @Override
  public String getSwaggerURL() {
    return "https://files.aisandbox.dev/swagger/mine.yaml";
  }

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[] {scenarioSalt, mineHunterBoardSize};
  }

  @Override
  public ScenarioRuntime getRuntime() {
    MineHunterRuntime runtime = new MineHunterRuntime(spriteLoader);
    if (scenarioSalt.getValue() != 0) {
      runtime.setRandom(new Random(scenarioSalt.getValue()));
    }
    runtime.setBoardSize(mineHunterBoardSize.getOptionIndex());
    return runtime;
  }
}
