package dev.aisandbox.client.scenarios.mine;

import com.dooapp.fxform.annotation.NonVisual;
import dev.aisandbox.client.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioType;
import dev.aisandbox.client.sprite.SpriteLoader;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MineHunterScenario implements Scenario {

  @NonVisual
  private static final Logger LOG = LoggerFactory.getLogger(MineHunterScenario.class.getName());

  // configuration
  @Getter @Setter private Long scenarioSalt = 0l;
  @Getter @Setter private SizeEnum mineHunterBoardSize = SizeEnum.MEDIUM;

  @Override
  public ScenarioType getGroup() {
    return ScenarioType.INTRODUCTION;
  }

  @Override
  public String getName() {
    return "Mine Hunter";
  }

  @Override
  public String getOverview() {
    return "Find the mines in a grid using deduction";
  }

  @Override
  public String getDescription() {
    return "Mine Hunter pits the AI against a minefield! A known quantity of mines has been distributed across a grid of squares and the AI agent must work out where they are. To help, each uncovered square will show how many mines are in the surrounding squares.";
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
  public String getImageReference() {
    return "/dev/aisandbox/client/scenarios/mine/sample.png";
  }

  @Override
  public String getScenarioURL() {
    return "https://www.aisandbox.dev/scenarios/MineHunter.html";
  }

  @Override
  public String getSwaggerURL() {
    return "https://www.aisandbox.dev/scenarios/mine.yaml";
  }

  @Autowired @NonVisual SpriteLoader spriteLoader;

  @NonVisual private MineHunterThread thread = null;

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
    LOG.info("Starting run thread");
    thread =
        new MineHunterThread(
            agentList.get(0), output, ui, rand, spriteLoader, mineHunterBoardSize, stepCount);
    thread.start();
  }

  @Override
  public void stopSimulation() {
    if (thread != null) {
      thread.stopSimulation();
    }
  }

  @Override
  public void joinSimulation() {
    LOG.info("Joining simulation");
    if (thread != null) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        LOG.warn("Interrupted!", e);
        // Restore interrupted state...
        Thread.currentThread().interrupt();
      }
      thread = null;
    }
  }

  @Override
  public boolean isSimulationRunning() {
    if (thread == null) {
      return false;
    }
    return thread.isRunning();
  }
}
