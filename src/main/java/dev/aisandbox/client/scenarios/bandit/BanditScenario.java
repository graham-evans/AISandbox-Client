package dev.aisandbox.client.scenarios.bandit;

import dev.aisandbox.client.parameters.MapParameter;
import dev.aisandbox.client.scenarios.BaseScenario;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import java.util.List;
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
        "Descriptive Text.",
        "/dev/aisandbox/client/scenarios/bandit/sample.png",
        1,
        1,
        "TBD",
        "TBD");
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
  MapParameter<Double> banditMean =
      new MapParameter<>(
          "bandit.mean",
          List.of("N(0,1)", "N(1,1)", "N(4,1)"),
          List.of(0.0, 1.0, 4.0),
          "Bandit Mean",
          null);
  MapParameter<Double> banditVar =
      new MapParameter<>(
          "bandit.var", List.of("1", "2"), List.of(1.0, 2.0), "Bandit variance", null);

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[] {banditCount, banditPulls, banditMean, banditVar};
  }

  @Override
  public ScenarioRuntime getRuntime() {
    return null;
  }
}
