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
public class ZebraScenario implements Scenario {

  @NonVisual
  private static final Logger LOG = LoggerFactory.getLogger(ZebraScenario.class.getName());

  // configuration
  @Getter @Setter private Long scenarioSalt = 0l;

  @Getter @Setter
  private ZebraPuzzleDifficultyEnum zebraDifficulty = ZebraPuzzleDifficultyEnum.MEDIUM;

  @Getter @Setter private boolean zebraMultipleGuesses = true;

  @Override
  public ScenarioType getGroup() {
    return ScenarioType.TEXT;
  }

  @Override
  public String getName() {
    return "Zebra Puzzle";
  }

  @Override
  public String getOverview() {
    return "Text parsing and constraint solving puzzles, based on the classic Zebra Puzzle.";
  }

  @Override
  public String getDescription() {
    return "Long description";
  }

  @Override
  public String getImageReference() {
    return "/dev/aisandbox/client/scenarios/zebra/sample.png";
  }

  @Override
  public int getMinAgentCount() {
    return 1;
  }

  @Override
  public int getMaxAgentCount() {
    return 1;
  }

  @Override
  public void startSimulation(
      List<Agent> agentList, GameRunController ui, FrameOutput output, Long stepCount) {}

  @Override
  public void stopSimulation() {}

  @Override
  public boolean isSimulationRunning() {
    return false;
  }

  @Override
  public void joinSimulation() {}

  @Override
  public String getScenarioURL() {
    return "https://aisandbox.dev/scenarios-zebra/";
  }

  @Override
  public String getSwaggerURL() {
    return "https://files.aisandbox.dev/swagger/zebra.yaml";
  }

  @Override
  public boolean isBeta() {
    return false;
  }
}
