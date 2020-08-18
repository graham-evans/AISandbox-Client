package dev.aisandbox.client.parameters;

import dev.aisandbox.client.scenarios.ScenarioParameter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import lombok.Getter;

public class BooleanParameter implements ScenarioParameter {

  private final String parameterKey;
  private BooleanProperty value = new SimpleBooleanProperty(false);
  @Getter private final String name;
  @Getter private final String tooltip;

  public BooleanParameter(String parameterKey, boolean startingValue, String name, String tooltip) {
    this.parameterKey = parameterKey;
    value.set(startingValue);
    this.name = name;
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
    cb.setText(name);

    // bind values
    cb.selectedProperty().bindBidirectional(value);
    return cb;
  }

  @Override
  public void setParsableValue(String value) throws ParameterParseException {
    try {
      this.value.set(Boolean.parseBoolean(value));
    } catch (Exception e) {
      throw new ParameterParseException(
          "Can't parse " + value + " to a boolean for " + parameterKey);
    }
  }
}
