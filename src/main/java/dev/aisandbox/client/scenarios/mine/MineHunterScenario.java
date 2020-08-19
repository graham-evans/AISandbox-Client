package dev.aisandbox.client.scenarios.mine;

import dev.aisandbox.client.parameters.EnumerationParameter;
import dev.aisandbox.client.parameters.LongParameter;
import dev.aisandbox.client.scenarios.BaseScenario;
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
public class MineHunterScenario extends BaseScenario implements Scenario {

  private final SpriteLoader spriteLoader;

  @Autowired
  public MineHunterScenario(SpriteLoader spriteLoader) {
    super(
        "mine",
        "Mine Hunter",
        ScenarioType.INTRODUCTION,
        "Find the mines in a grid using deduction",
        "Mine Hunter pits the AI against a minefield! A known quantity of mines has been"
            + " distributed across a grid of squares and the AI agent must work out where they are."
            + " To help, each uncovered square will show how many mines are in the surrounding"
            + " squares.",
        "/dev/aisandbox/client/scenarios/mine/sample.png",
        1,
        1,
        "https://aisandbox.dev/scenarios-mine/",
        "https://files.aisandbox.dev/swagger/mine.yaml");
    this.spriteLoader = spriteLoader;
  }

  // configuration
  private LongParameter scenarioSalt =
      new LongParameter("mine.salt", 0, "Random Salt", "Set this to zero for a random maze.");
  private EnumerationParameter<MineSize> mineHunterBoardSize =
      new EnumerationParameter<>(
          "mine.size", MineSize.SMALL, "Board size", "Select the size of the boards to generate");

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
    runtime.setBoardSize(mineHunterBoardSize.getValue());
    return runtime;
  }
}
