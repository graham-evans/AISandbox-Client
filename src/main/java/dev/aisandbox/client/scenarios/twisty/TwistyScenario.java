package dev.aisandbox.client.scenarios.twisty;

import dev.aisandbox.client.parameters.BooleanParameter;
import dev.aisandbox.client.parameters.LongParameter;
import dev.aisandbox.client.parameters.OptionParameter;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import dev.aisandbox.client.scenarios.twisty.puzzles.Cube3x3x3;
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
      new String[] {"Cube 3x3x3 (OBTM)", "Cube 2x2x2 (OBTM)"};

  private OptionParameter twistyType = new OptionParameter("twisty.type", PUZZLE_TYPES);
  private LongParameter scenarioSalt = new LongParameter("twisty.salt", 0, "Random Salt", null);
  private BooleanParameter twistyStartSolved =
      new BooleanParameter("twisty.solved", false, "Start Solved", null);

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
    TwistyRuntime runtime = new TwistyRuntime();
    runtime.setPuzzle(new Cube3x3x3());
    if (scenarioSalt.getValue() != 0) {
      runtime.setRandom(new Random(scenarioSalt.getValue()));
    }
    runtime.setStartSolved(twistyStartSolved.getValue());
    return runtime;
  }
}
