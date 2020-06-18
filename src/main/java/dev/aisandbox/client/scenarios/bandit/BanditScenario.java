package dev.aisandbox.client.scenarios.bandit;

import dev.aisandbox.client.parameters.OptionParameter;
import dev.aisandbox.client.scenarios.BaseScenario;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BanditScenario extends BaseScenario implements Scenario {

  public BanditScenario() {
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

  OptionParameter banditCountOption =
      new OptionParameter("bandit.count", new String[] {"5", "10", "20", "50"});
  OptionParameter banditPulls =
      new OptionParameter("bandit.pulls", new String[] {"100", "500", "1000", "2000", "5000"});
  OptionParameter banditType =
      new OptionParameter("bandit.type", new String[] {"Stochastic Normal", "Wandering", "Drop"});

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[] {banditType, banditCountOption, banditPulls};
  }

  @Override
  public ScenarioRuntime getRuntime() {
    return null;
  }
}
