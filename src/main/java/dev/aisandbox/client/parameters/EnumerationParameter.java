package dev.aisandbox.client.parameters;

import dev.aisandbox.client.scenarios.ScenarioParameter;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumerationParameter<T extends Enum<T>> implements ScenarioParameter {

  @Getter private T value;

  @Getter private final String parameterKey;

  @Getter private final String name;
  @Getter private final String tooltip;

  /**
   * Constructor for a choice of enumerated values.
   *
   * @param key the name of the parameter
   * @param startChoice the <i>Enum</i> to choose a value from
   * @param name The label to show in the UI
   * @param tooltip A description
   */
  public EnumerationParameter(String key, T startChoice, String name, String tooltip) {
    parameterKey = key;
    value = startChoice;
    this.name = name;
    this.tooltip = tooltip;
  }

  @Override
  public Node getParameterControl() {
    // define controls
    HBox pane = new HBox();
    pane.setSpacing(5.0);
    pane.setAlignment(Pos.CENTER_LEFT);
    // text label
    Label label = new Label(name);
    label.setMaxWidth(Double.MAX_VALUE);
    // combo box
    ComboBox<T> optionControl = new ComboBox<>();
    for (T t : value.getDeclaringClass().getEnumConstants()) {
      optionControl.getItems().add(t);
    }
    optionControl.getSelectionModel().select(value);
    // ui
    pane.getChildren().add(label);
    pane.getChildren().add(optionControl);
    HBox.setHgrow(label, Priority.ALWAYS);
    // bind options
    optionControl
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((options, oldValue, newValue) -> value = newValue);
    return pane;
  }

  @Override
  public void setParsableValue(String val) throws ParameterParseException {
    log.info("Setting {} to {}", parameterKey, val);
    boolean found = false;

    for (T t : value.getDeclaringClass().getEnumConstants()) {
      log.info("Testing {} ", t);
      if (t.name().equalsIgnoreCase(val)) {
        found = true;
        value = t;
      }
    }
    if (!found) {
      throw new ParameterParseException("Can't parse " + val + " to enumerated value");
    }
  }

  /**
   * Get a map of the potential values name:Description.
   *
   * @return The map of values and their description (returned by <i>toString()</i>)
   */
  public Map<String, String> getEnumerationOptions() {
    Map<String, String> options = new HashMap<>();
    for (T t : value.getDeclaringClass().getEnumConstants()) {
      options.put(t.name(), t.toString());
    }
    return options;
  }
}
