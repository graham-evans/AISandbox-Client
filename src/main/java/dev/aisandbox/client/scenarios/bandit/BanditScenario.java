package dev.aisandbox.client.scenarios.bandit;

import dev.aisandbox.client.parameters.BooleanParameter;
import dev.aisandbox.client.parameters.LongParameter;
import dev.aisandbox.client.parameters.MapParameter;
import dev.aisandbox.client.scenarios.BaseScenario;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BanditScenario extends BaseScenario implements Scenario {

  public BanditScenario() {
    // setup basic settings
    super(
        "bandit",
        "Multi-Armed Bandit Problem",
        ScenarioType.INTRODUCTION,
        "Choose between exploring and exploiting information to get the best outcomes.",
        "The Multi-Armed Bandit is a classic reinforcement learning problem where an"
            + " algorithm must select which slot machine (each with a different pay-out probability)"
            + " to play. The AI must choose between exploring different options to better predict"
            + " how each machine works and exploiting what it knows by picking the machine with the"
            + " highest reward.",
        "/dev/aisandbox/client/scenarios/bandit/sample.png",
        1,
        1,
        "https://aisandbox.dev/scenarios-bandit/",
        "https://files.aisandbox.dev/swagger/bandit.yaml");
  }

  MapParameter<Integer> banditCount =
      new MapParameter<>(
          "bandit.count",
          List.of("5", "10", "20", "50"),
          List.of(5, 10, 20, 50),
          "Number of Bandits",
          null);

  MapParameter<Integer> banditPulls =
      new MapParameter<>(
          "bandit.pulls",
          List.of("100", "500", "1000", "2000", "5000"),
          List.of(100, 500, 1000, 2000, 5000),
          "Number of pulls",
          null);
  LongParameter banditSalt = new LongParameter("bandit.salt", 0, "Random Salt", null);
  BooleanParameter banditSkip =
      new BooleanParameter(
          "bandit.skip",
          false,
          "Skip intermediate frames",
          "Dont redraw the screen for every pull (much quicker)");

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[] {banditCount, banditPulls, banditSkip};
  }

  @Override
  public ScenarioRuntime getRuntime() {
    Random random;
    if (banditSalt.getValue() != 0) {
      random = new Random(banditSalt.getValue());
    } else {
      random = new Random();
    }
    return new BanditRuntime(
        random,
        banditCount.getSelectedValue(),
        banditPulls.getSelectedValue(),
        banditSkip.getValue());
  }
}
