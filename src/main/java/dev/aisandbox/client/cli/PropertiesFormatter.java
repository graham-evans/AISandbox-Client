package dev.aisandbox.client.cli;

import dev.aisandbox.client.parameters.EnumerationParameter;
import dev.aisandbox.client.parameters.NumberEnumerationParameter;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioComparator;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PropertiesFormatter {

  private final List<Scenario> scenarioList;

  @Autowired
  public PropertiesFormatter(List<Scenario> scenarioList) {
    this.scenarioList = scenarioList;
  }

  /**
   * Generate a text description of all scenarios and their options.
   *
   * @return A multi-line String.
   */
  public String getPropertiesDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append("AI Sandbox.dev\n\n");
    sb.append("Properties\n");
    // TODO agent properties
    // TODO output options
    // Scenario properties
    scenarioList.sort(new ScenarioComparator());
    for (Scenario scenario : scenarioList) {
      sb.append(getScenarioDescription(scenario));
    }
    return sb.toString();
  }

  private String getScenarioDescription(Scenario scenario) {
    StringBuilder sb = new StringBuilder();
    sb.append(scenario.getName());
    sb.append("\n\n");
    sb.append(scenario.getOverview());
    sb.append("\n\n");
    for (ScenarioParameter p : scenario.getParameterArray()) {
      sb.append(formatParameter(p));
    }
    sb.append("\n");
    return sb.toString();
  }

  private String formatParameter(ScenarioParameter p) {
    StringBuilder sb = new StringBuilder();
    sb.append(" ");
    sb.append(p.getParameterKey());
    sb.append(" - ");
    sb.append(p.getName());
    sb.append("\n  ");
    sb.append(p.getTooltip());
    sb.append("\n");
    if (p instanceof NumberEnumerationParameter) {
      sb.append(formatNumberEnumerationParameter((NumberEnumerationParameter) p));
    } else if (p instanceof EnumerationParameter) {
      sb.append(formatEnumerationParameter((EnumerationParameter) p));
    }
    return sb.toString();
  }

  private static String formatNumberEnumerationParameter(NumberEnumerationParameter<?> p) {
    StringBuilder sb = new StringBuilder();
    sb.append("  Options ");
    List<String> vals = new ArrayList<>();
    for (String num : p.getEnumerationOptions().values()) {
      if (num.equals(p.getValue().toString())) {
        vals.add(num + " (default)");
      } else {
        vals.add(num);
      }
    }
    sb.append(String.join(", ", vals));
    sb.append("\n");
    return sb.toString();
  }

  private static String formatEnumerationParameter(EnumerationParameter<?> p) {
    StringBuilder sb = new StringBuilder();
    sb.append("  Options\n");
    Map<String, String> options = p.getEnumerationOptions();
    for (String op : options.keySet()) {
      String description = options.get(op);
      sb.append("   ");
      sb.append(op);
      sb.append(" : ");
      sb.append(description);
      if (op.equals(p.getValue().name())) {
        sb.append(" (default)");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
