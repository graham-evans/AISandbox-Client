package dev.aisandbox.client.scenarios.twisty;

import com.dooapp.fxform.annotation.NonVisual;
import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioType;
import java.util.List;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TwistyScenario implements Scenario {

  private PuzzleType twistyType = PuzzleType.CUBE3;
  private boolean twistyMultipleSteps = true;

  @NonVisual // dont show this on the UI
  private TwistyThread thread = null;

  @Override
  public ScenarioType getGroup() {
    return ScenarioType.INTERMEDIATE;
  }

  @Override
  public String getName() {
    return "Twisty Puzzle";
  }

  @Override
  public String getOverview() {
    return "Puzzles based on rotating layers and circles";
  }

  @Override
  public String getDescription() {
    return "Puzzles based on rotating layers and circles";
  }

  @Override
  public String getImageReference() {
    return "/dev/aisandbox/client/scenarios/twisty/sample.png";
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
      List<Agent> agentList, GameRunController ui, FrameOutput output, Long stepCount) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void stopSimulation() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isSimulationRunning() {
    return (thread != null) && (thread.isRunning());
  }

  @Override
  public void joinSimulation() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getScenarioURL() {
    return "https://aisandbox.dev/";
  }

  @Override
  public String getSwaggerURL() {
    return "https://aisandbox.dev/";
  }

  @Override
  public boolean isBeta() {
    return false;
  }
}
