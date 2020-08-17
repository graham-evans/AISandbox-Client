package dev.aisandbox.client.parameters;

import dev.aisandbox.client.scenarios.ScenarioParameter;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OptionParameter implements ScenarioParameter {

  @Getter private final String parameterKey;

  @Getter
  private ObservableList<String> optionList = FXCollections.observableList(new ArrayList<>());

  @Getter private String name = null;
  @Getter private String tooltip = null;

  private IntegerProperty selectedIndex = new SimpleIntegerProperty(0);

  public OptionParameter(String key, String[] options) {
    parameterKey = key;
    for (int i = 0; i < options.length; i++) {
      optionList.add(options[i]);
    }
  }

  public OptionParameter(String key, String[] options, String name, String tooltip) {
    parameterKey = key;
    for (int i = 0; i < options.length; i++) {
      optionList.add(options[i]);
    }
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
    ComboBox<String> optionControl = new ComboBox<>();
    optionControl.setItems(optionList);
    // ui
    pane.getChildren().add(label);
    pane.getChildren().add(optionControl);
    pane.setHgrow(label, Priority.ALWAYS);
    // bind options
    int idx = selectedIndex.get();
    selectedIndex.bind(optionControl.getSelectionModel().selectedIndexProperty());
    optionControl.getSelectionModel().select(idx);
    // return parameter
    return pane;
  }

  public int getOptionIndex() {
    return selectedIndex.get();
  }

  public String getOptionString() {
    return optionList.get(selectedIndex.get());
  }

  @Override
  public void setParsableValue(String value) throws ParameterParseException {
    log.info("Setting value of {} to '{}'", parameterKey, value);
    if (value == null) {
      throw new IllegalArgumentException("No option selected for " + parameterKey);
    }
    if (value.matches("^[0-9]$")) {
      try {
        int v = Integer.parseInt(value);
        if ((v > -1) && (v < optionList.size())) {
          selectedIndex.set(v);
          return;
        } else {
          throw new IllegalArgumentException(
              "Index must be in range 0-" + optionList.size() + " for " + parameterKey);
        }
      } catch (Exception e) {
        throw new IllegalArgumentException("Number must be parsable for " + parameterKey);
      }
    }
    // check for text match
    for (int i = 0; i < optionList.size(); i++) {
      if (optionList.get(i).equalsIgnoreCase(value)) {
        selectedIndex.set(i);
        return;
      }
    }
    // no matched
    throw new ParameterParseException("Unknown option for " + parameterKey);
  }
}
