package dev.aisandbox.client.fx;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.scenarios.Scenario;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * <p>GameChoiceController class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
public class GameChoiceController {

    private static final Logger LOG = Logger.getLogger(GameChoiceController.class.getName());
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private List<Scenario> scenarioList;
    @Autowired
    private RuntimeModel model;
    @Autowired
    private FXTools fxtools;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Scenario> gameList;

    @FXML
    private Label titleText;

    @FXML
    private Label gameNameField;

    @FXML
    private Label gameIntroField;

    @FXML
    private ImageView gameImageField;

    @FXML
    private TextFlow gameDescription;

    @FXML
    private Hyperlink scenarioLink;

    @FXML
    private Hyperlink swaggerLink;

    @FXML
    private Button nextButton;

    @FXML
    void initialize() {

        assert gameList != null : "fx:id=\"gameList\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert titleText != null : "fx:id=\"titleText\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert gameNameField != null : "fx:id=\"gameNameField\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert gameIntroField != null : "fx:id=\"gameIntroField\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert gameImageField != null : "fx:id=\"gameImageField\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert gameDescription != null : "fx:id=\"gameDescription\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert scenarioLink != null : "fx:id=\"scenarioLink\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert swaggerLink != null : "fx:id=\"swaggerLink\" was not injected: check your FXML file 'GameChoice.fxml'.";
        assert nextButton != null : "fx:id=\"nextButton\" was not injected: check your FXML file 'GameChoice.fxml'.";

        LOG.info("Initialising Game Choise controller");
        if (scenarioList == null) {
            LOG.severe("scenarioList is null");
        } else {
            LOG.log(Level.INFO, "There are {0} scenarios", scenarioList.size());
            // add scenarios to the list
            gameList.getItems().addAll(scenarioList);
            // set the formatting
            gameList.setCellFactory(cellListView -> new ScenarioCell());
            // setup selection change code
            gameList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        gameNameField.setText(newValue.getName());
                        gameIntroField.setText(newValue.getOverview());
                        gameDescription.getChildren().clear();
                        gameDescription.getChildren().add(convertStringToText(newValue.getDescription()));
                        if (newValue.getImageReference() != null) {
                            gameImageField.setImage(new Image(newValue.getImageReference()));
                        } else {
                            gameImageField.setImage(null);
                        }

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

    private Text convertStringToText(String s) {
        Text t = new Text(s);
        return t;
    }

    @FXML
    void launchScenarioLink(ActionEvent event) {
        launchBrowser("https://www.aisandbox.dev/");
    }

    @FXML
    void launchSwaggerLink(ActionEvent event) {
        launchBrowser("https://www.aisandbox.dev/");
    }

    private void launchBrowser(String link) {
        try {
            Desktop.getDesktop().browse(new URL(link).toURI());
        } catch (IOException e) {
            LOG.log(Level.SEVERE,"IO Error spawning browser",e);
        } catch (URISyntaxException e) {
            LOG.log(Level.SEVERE,"URL Error spawning browser",e);
        }
    }

    @FXML
    void nextAction(ActionEvent event) {
        LOG.info("Moving to game options screen");
        fxtools.moveToScreen(event, "/dev/aisandbox/client/fx/GameOptions.fxml");
    }

}
