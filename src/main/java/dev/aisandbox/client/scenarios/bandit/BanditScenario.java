package dev.aisandbox.client.scenarios.bandit;

import dev.aisandbox.client.parameters.BooleanParameter;
import dev.aisandbox.client.parameters.EnumerationParameter;
import dev.aisandbox.client.parameters.LongParameter;
import dev.aisandbox.client.parameters.NumberEnumerationParameter;
import dev.aisandbox.client.scenarios.BaseScenario;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import dev.aisandbox.client.scenarios.bandit.model.BanditCountEnumeration;
import dev.aisandbox.client.scenarios.bandit.model.BanditNormalEnumeration;
import dev.aisandbox.client.scenarios.bandit.model.BanditPullEnumeration;
import dev.aisandbox.client.scenarios.bandit.model.BanditStdEnumeration;
import dev.aisandbox.client.scenarios.bandit.model.BanditUpdateEnumeration;
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
            + " algorithm must select which slot machine (each with a different pay-out "
            + " probability) to play. The AI must choose between exploring different options to "
            + " better predict"
            + " how each machine works and exploiting what it knows by picking the machine with the"
            + " highest reward.",
        "/dev/aisandbox/client/scenarios/bandit/sample.png",
        1,
        1,
        "https://aisandbox.dev/scenarios-bandit/",
        "https://files.aisandbox.dev/swagger/bandit.yaml");
  }

  NumberEnumerationParameter<BanditCountEnumeration> banditCount =
      new NumberEnumerationParameter<>(
          "bandit.count",
          BanditCountEnumeration.TEN,
          "# Bandits",
          "The number of bandits to include");

  NumberEnumerationParameter<BanditPullEnumeration> banditPulls =
      new NumberEnumerationParameter<>(
          "bandit.pulls",
          BanditPullEnumeration.ONE_THOUSAND,
          "# Pulls",
          "How many pulls in each test");

  EnumerationParameter<BanditNormalEnumeration> banditNormal =
      new EnumerationParameter<>(
          "bandit.normal",
          BanditNormalEnumeration.NORMAL_0_1,
          "Bandit Normals",
          "How the normals for each bandit are chosen");
  EnumerationParameter<BanditStdEnumeration> banditStd =
      new EnumerationParameter<>(
          "bandit.std",
          BanditStdEnumeration.ONE,
          "Bandit Standard Deviation",
          "How the std for each bandit are chosen");
  EnumerationParameter<BanditUpdateEnumeration> banditUpdate =
      new EnumerationParameter<>(
          "bandit.update",
          BanditUpdateEnumeration.FIXED,
          "Update Rule",
          "How the bandits are updated after each step");

  LongParameter banditSalt =
      new LongParameter("bandit.salt", 0, "Random Salt", "Set this to zero for a random test.");

  BooleanParameter banditSkip =
      new BooleanParameter(
          "bandit.skip",
          false,
          "Skip intermediate frames",
          "Dont redraw the screen for every pull (much quicker)");

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[] {
      banditCount, banditPulls, banditNormal, banditStd, banditUpdate, banditSalt, banditSkip
    };
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
        banditCount.getValue().getNumber(),
        banditPulls.getValue().getNumber(),
        banditNormal.getValue(),
        banditStd.getValue(),
        banditUpdate.getValue(),
        banditSkip.getValue());
  }
}
