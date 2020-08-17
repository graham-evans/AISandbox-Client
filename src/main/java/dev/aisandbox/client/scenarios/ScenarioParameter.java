package dev.aisandbox.client.scenarios;

import dev.aisandbox.client.parameters.ParameterParseException;
import javafx.scene.Node;

public interface ScenarioParameter {
  public String getParameterKey();

  public Node getParameterControl();

  public void setParsableValue(String value) throws ParameterParseException;

  public String getName();

  public String getTooltip();
}
