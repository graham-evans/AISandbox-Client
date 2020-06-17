package dev.aisandbox.client.scenarios;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseScenario implements Scenario {

  @Getter private final String id;
  @Getter private final String name;
  @Getter private final ScenarioType group;
  @Getter private final String overview;
  @Getter private final String description;
  @Getter private final String imageReference;
  @Getter private final int minAgentCount;
  @Getter private final int maxAgentCount;
  @Getter private final String scenarioURL;
  @Getter private final String swaggerURL;
}
