package dev.aisandbox.client.scenarios.zebra;

import com.dooapp.fxform.annotation.NonVisual;
import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioType;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// @Component
/**
 * ZebraScenario class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class ZebraScenario implements Scenario {

  @NonVisual
  private static final Logger LOG = LoggerFactory.getLogger(ZebraScenario.class.getName());

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
  public void startSimulation(
      List<Agent> agentList, GameRunController ui, FrameOutput output, Long stepCount) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public void stopSimulation() {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public boolean isSimulationRunning() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public void joinSimulation() {
    throw new UnsupportedOperationException();
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

  /** {@inheritDoc} */
  @Override
  public boolean isBeta() {
    return false;
  }
}
