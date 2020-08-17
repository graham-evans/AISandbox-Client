package dev.aisandbox.client.parameters;

import dev.aisandbox.client.scenarios.ScenarioParameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
public class MapParameter<T> implements ScenarioParameter {

  @Getter private final String parameterKey;

  private ObservableList<String> optionList = FXCollections.observableList(new ArrayList<>());
  private List<T> objectList = new ArrayList<>();

  @Getter private final String name;
  @Getter private final String tooltip;
  private IntegerProperty selectedIndex = new SimpleIntegerProperty(0);

  public MapParameter(String key, Map<String, T> optionsMap, String name, String tooltip) {
    parameterKey = key;
    for (Entry<String, T> entry : optionsMap.entrySet()) {
      optionList.add(entry.getKey());
      objectList.add(entry.getValue());
    }
    this.name = name;
    this.tooltip = tooltip;
  }

  public MapParameter(
      String key, List<String> optionsList, List<T> objectsList, String name, String tooltip) {
    parameterKey = key;
    if (optionsList.size() != objectsList.size()) {
      throw new IllegalStateException("Options and objects different size");
    }
    optionList.addAll(optionsList);
    objectList.addAll(objectsList);
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

  public int getSelectedIndex() {
    return selectedIndex.get();
  }

  public String getSelectedString() {
    return optionList.get(selectedIndex.get());
  }

  public T getSelectedValue() {
    return objectList.get(selectedIndex.get());
  }

  @Override
  public void setParsableValue(String value) throws ParameterParseException {
    log.info("Setting value of {} to '{}'", parameterKey, value);
    if (value == null) {
      throw new IllegalArgumentException("No option selected for " + parameterKey);
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
