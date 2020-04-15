package dev.aisandbox.client.scenarios.twisty;

import com.dooapp.fxform.annotation.NonVisual;
import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioType;
import java.util.List;
import java.util.Random;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * TwistyScenario class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
@Data
public class TwistyScenario implements Scenario {

  @NonVisual
  private static final Logger log = LoggerFactory.getLogger(TwistyScenario.class.getName());

  private PuzzleType twistyType = PuzzleType.CUBE3;
  private boolean twistyMultipleSteps = true;
  private Long scenarioSalt = 0l;

  @NonVisual // dont show this on the UI
  private TwistyThread thread = null;

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

  /** {@inheritDoc} */
  @Override
  public void startSimulation(
      List<Agent> agentList, GameRunController ui, FrameOutput output, Long stepCount) {
    // create random number generator
    Random rand;
    if (scenarioSalt == 0) {
      rand = new Random();
    } else {
      rand = new Random(scenarioSalt);
    }
    log.info("Starting run thread");
    thread = new TwistyThread(agentList.get(0), output, ui, rand, twistyType, stepCount);
    thread.start();
  }

  /** {@inheritDoc} */
  @Override
  public void stopSimulation() {
    if (thread != null) {
      thread.stopSimulation();
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean isSimulationRunning() {
    return (thread != null) && (thread.isRunning());
  }

  /** {@inheritDoc} */
  @Override
  public void joinSimulation() {
    log.info("Joining simulation");
    if (thread != null) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        log.warn("Interrupted!", e);
        // Restore interrupted state...
        Thread.currentThread().interrupt();
      }
      thread = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  public String getScenarioURL() {
    return "https://aisandbox.dev/";
  }

  /** {@inheritDoc} */
  @Override
  public String getSwaggerURL() {
    return "https://aisandbox.dev/";
  }

  /** {@inheritDoc} */
  @Override
  public boolean isBeta() {
    return false;
  }
}
