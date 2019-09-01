package dev.aisandbox.client.fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import dev.aisandbox.client.scenarios.Scenario;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScenarioCell extends ListCell<Scenario> {

    private static Logger LOG = Logger.getLogger(ScenarioCell.class.getName());

    @FXML
    private Label scenarioName;

    @FXML
    private Label scenarioDescription;

    @FXML
    private Pane colourPane;

    @FXML
    private BorderPane root;

    FXMLLoader mLLoader = null;

    @Override
    protected void updateItem(Scenario scenario, boolean empty) {
        super.updateItem(scenario, empty);

        if (empty || scenario == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/dev/aisandbox/client/fx/ScenarioCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    LOG.log(Level.SEVERE, "Error loading FXML", e);
                }

            }

            scenarioName.setText(scenario.getName(Locale.UK));
            scenarioDescription.setText(scenario.getOverview(Locale.UK));

            setText(null);
            setGraphic(root);
        }

    }
}
