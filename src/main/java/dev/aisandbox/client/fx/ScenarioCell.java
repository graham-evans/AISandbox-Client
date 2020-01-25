package dev.aisandbox.client.fx;

import dev.aisandbox.client.scenarios.Scenario;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ScenarioCell class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class ScenarioCell extends ListCell<Scenario> {

  private static final Logger LOG = LoggerFactory.getLogger(ScenarioCell.class.getName());

  @FXML private Label scenarioName;

  @FXML private Label scenarioDescription;

  @FXML private Pane colourPane;

  @FXML private BorderPane root;

  private FXMLLoader mLLoader = null;

  /** {@inheritDoc} */
  @Override
  protected void updateItem(Scenario scenario, boolean empty) {
    super.updateItem(scenario, empty);

    if (empty || scenario == null) {

      setText(null);
      setGraphic(null);

    } else {
      if (mLLoader == null) {
        mLLoader =
            new FXMLLoader(getClass().getResource("/dev/aisandbox/client/fx/ScenarioCell.fxml"));
        mLLoader.setController(this);

        try {
          mLLoader.load();
        } catch (IOException e) {
          LOG.error("Error loading FXML", e);
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
