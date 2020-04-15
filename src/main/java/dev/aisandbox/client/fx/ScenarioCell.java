package dev.aisandbox.client.fx;

import dev.aisandbox.client.scenarios.Scenario;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

/**
 * ScenarioCell class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
public class ScenarioCell extends ListCell<Scenario> {

  @FXML private Label scenarioName;

  @FXML private Label scenarioDescription;

  @FXML private Pane colourPane;

  @FXML private BorderPane root;

  private FXMLLoader fxmlLoader = null;

  /**
   * {@inheritDoc}
   *
   * <p>Regenerate the cell based on the current scenario.
   */
  @Override
  protected void updateItem(Scenario scenario, boolean empty) {
    super.updateItem(scenario, empty);

    if (empty || scenario == null) {

      setText(null);
      setGraphic(null);

    } else {
      if (fxmlLoader == null) {
        fxmlLoader =
            new FXMLLoader(getClass().getResource("/dev/aisandbox/client/fx/ScenarioCell.fxml"));
        fxmlLoader.setController(this);

        try {
          fxmlLoader.load();
        } catch (IOException e) {
          log.error("Error loading FXML", e);
        }
      }

      scenarioName.setText(scenario.getName());
      scenarioDescription.setText(scenario.getOverview());
      colourPane.setBackground(
          new Background(
              new BackgroundFill(
                  scenario.getGroup().getTypeColour(), CornerRadii.EMPTY, Insets.EMPTY)));

      setText(null);
      setGraphic(root);
    }
  }
}
