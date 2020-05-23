package dev.aisandbox.client.scenarios.twisty;

import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import dev.aisandbox.client.scenarios.parameters.BooleanParameter;
import dev.aisandbox.client.scenarios.parameters.LongParameter;
import dev.aisandbox.client.scenarios.parameters.OptionParameter;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

  private OptionParameter twistyType = new OptionParameter("twisty.type", new String[0]);
  private LongParameter scenarioSalt = new LongParameter("twisty.salt", 0, "Random Salt", null);
  private BooleanParameter twistyStartSolved =
      new BooleanParameter("twisty.solved", false, "Start Solved", null);

  @Autowired
  public TwistyScenario(List<TwistyPuzzle> puzzles) {
    // populate options
    for (TwistyPuzzle tp : puzzles) {
      twistyType.getOptionList().add(tp.getPuzzleName());
    }
  }

  /** {@inheritDoc} */
  @Override
  public ScenarioType getGroup() {
    return ScenarioType.INTERMEDIATE;
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

  //  /** {@inheritDoc} */
  //  @Override
  //  public void startSimulation(
  //      List<Agent> agentList, GameRunController ui, FrameOutput output, Long stepCount) {
  //    // create random number generator
  //    Random rand;
  //    if (scenarioSalt == 0) {
  //      rand = new Random();
  //    } else {
  //      rand = new Random(scenarioSalt);
  //    }
  //    log.info("Starting run thread");
  //    thread =
  //        new TwistyThread(
  //            agentList.get(0), output, ui, rand, twistyType, stepCount, twistyStartSolved);
  //    thread.start();
  //  }

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
    return null;
  }
}
