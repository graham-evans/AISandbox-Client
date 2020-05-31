package dev.aisandbox.client.scenarios;

import javafx.scene.Node;

public interface ScenarioParameter {
  public String getParameterKey();

  public Node getParameterControl();

  public void setParsableValue(String value) throws IllegalArgumentException;
}
