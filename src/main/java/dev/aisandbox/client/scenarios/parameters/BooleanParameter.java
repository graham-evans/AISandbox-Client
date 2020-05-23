package dev.aisandbox.client.scenarios.parameters;

import dev.aisandbox.client.scenarios.ScenarioParameter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import lombok.Setter;

public class BooleanParameter implements ScenarioParameter {

  private final String parameterKey;
  private BooleanProperty value = new SimpleBooleanProperty(false);
  @Setter private String description;
  @Setter private String tooltip;

  public BooleanParameter(String parameterKey, boolean startingValue) {
    this.parameterKey = parameterKey;
    value.set(startingValue);
  }

  public BooleanParameter(
      String parameterKey, boolean startingValue, String description, String tooltip) {
    this.parameterKey = parameterKey;
    value.set(startingValue);
    this.description = description;
    this.tooltip = tooltip;
  }

  public boolean getValue() {
    return value.get();
  }

  @Override
  public String getParameterKey() {
    return parameterKey;
  }

  @Override
  public Node getParameterControl() {
    CheckBox cb = new CheckBox();
    // setup
    cb.setText(description);

    // bind values
    cb.selectedProperty().bindBidirectional(value);
    return cb;
  }

  @Override
  public void setParsableValue(String value) throws IllegalArgumentException {
    try {
      this.value.set(Boolean.parseBoolean(value));
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Can't parse " + value + " to a boolean for " + parameterKey);
    }
  }
}
