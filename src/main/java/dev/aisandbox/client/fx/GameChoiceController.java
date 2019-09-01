package dev.aisandbox.client.fx;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.scenarios.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GameChoiceController {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    List<Scenario> scenarioList;

    @Autowired
    RuntimeModel model;

    @Autowired
    FXTools fxtools;

    Logger LOG = Logger.getLogger(GameChoiceController.class.getName());

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Scenario> gameList;

    @FXML
    private Label TitleText;

    @FXML
    private Label gameNameField;

    @FXML
    private Label gameIntroField;

    @FXML
    private ImageView gameImageField;

    @FXML
    private Label gameDescriptionField;

    @FXML
    private Button NextButton;

    @FXML
    void initialize() {
        assert gameList != null : "fx:id=\"gameList\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert TitleText != null : "fx:id=\"TitleText\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert gameNameField != null : "fx:id=\"GameNameField\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert gameIntroField != null : "fx:id=\"GameIntroField\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert gameImageField != null : "fx:id=\"GameImageField\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert gameDescriptionField != null : "fx:id=\"GameDescriptionField\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert NextButton != null : "fx:id=\"NextButton\" was not injected: check your FXML file 'GameChoice.fxml'.";
        LOG.info("Initialising Game Choise controller");
        if (scenarioList == null) {
            LOG.severe("scenarioList is null");
        } else {
            LOG.info("There are " + scenarioList.size() + " scenarios");
            // add scenarios to the list
            gameList.getItems().addAll(scenarioList);
            // set the formatting
            gameList.setCellFactory(new Callback<ListView<Scenario>, ListCell<Scenario>>() {
                @Override
                public ListCell<Scenario> call(ListView<Scenario> studentListView) {
                    return new ScenarioCell();
                }
            });
            // setup selection change code
            gameList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        gameNameField.setText(newValue.getName(Locale.UK));
                        gameIntroField.setText(newValue.getOverview(Locale.UK));
                        gameDescriptionField.setText(newValue.getDescription(Locale.UK));
                        model.setScenario(newValue);
                    }
            );
            // select the first entry (or previous)
            if (model.getScenario() == null) {
                gameList.getSelectionModel().select(0);
            } else {
                gameList.getSelectionModel().select(model.getScenario());
            }
        }
    }


    @FXML
    void nextAction(ActionEvent event) {
        LOG.info("Moving to game options screen");
        fxtools.moveToScreen(event, "/dev/aisandbox/client/fx/GameOptions.fxml");
    }

}
