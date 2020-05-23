package dev.aisandbox.client.scenarios.parameters;

import dev.aisandbox.client.scenarios.ScenarioParameter;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.util.converter.NumberStringConverter;
import lombok.Setter;

public class LongParameter implements ScenarioParameter {

  private final String parameterKey;
  private LongProperty value = new SimpleLongProperty();
  @Setter private String description;
  @Setter private String tooltip;

  public LongParameter(String key, long startingValue) {
    parameterKey = key;
    value.set(startingValue);
  }

  public LongParameter(String key, long startingValue, String description, String tooltip) {
    parameterKey = key;
    value.set(startingValue);
    this.description = description;
    this.tooltip = tooltip;
  }

  @Override
  public String getParameterKey() {
    return parameterKey;
  }

  @Override
  public Node getParameterControl() {
    // define controls
    FlowPane pane = new FlowPane();
    Label label = new Label(description);
    TextField field = new TextField();
    pane.getChildren().add(label);
    pane.getChildren().add(field);
    // bind value
    field.textProperty().bindBidirectional(value, new NumberStringConverter());
    // return parameter
    return pane;
  }

  @Override
  public void setParsableValue(String val) throws IllegalArgumentException {
    try {
      value.set(Long.parseLong(val));
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Can't parse '" + val + "' to a long integer for " + parameterKey);
    }
  }

  public long getValue() {
    return value.get();
  }
}
