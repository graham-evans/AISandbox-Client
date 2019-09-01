package dev.aisandbox.client.fx;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import dev.aisandbox.client.Agent;
import dev.aisandbox.client.RuntimeModel;
import javafx.stage.Window;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class GameOptionsController {

    private static final Logger LOG = Logger.getLogger(GameOptionsController.class.getName());

    @Autowired
    RuntimeModel model;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    FXTools fxtools;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox optionsBox;

    @FXML
    private ListView<Agent> agentList;

    @FXML
    private ChoiceBox<?> outputType;

    @FXML
    private TextField outputDirectory;

    @FXML
    private Button outputDirectoryButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button removeAgentButton;

    @FXML
    private Button editAgentButton;

    @FXML
    private Button addAgentButton;

    @FXML
    void removeAgentEvent(ActionEvent event) {
        // get the selected agent
        Agent agent = agentList.getSelectionModel().getSelectedItem();
        LOG.log(Level.INFO, "Removing agent {}", agent);
        // remove it from the model
        model.getAgentList().remove(agent);
    }

    @FXML
    void addAgentAction(ActionEvent event) {
        Agent a = new Agent();
        // add agent to list
        model.getAgentList().add(a);
        // select agent
        agentList.getSelectionModel().select(a);
    }

    @FXML
    void lastAction(ActionEvent event) {
        LOG.info("Selecting game choice screen");
        fxtools.moveToScreen(event, "/dev/aisandbox/client/fx/GameChoice.fxml");
    }

    @FXML
    void nextAction(ActionEvent event) {
        LOG.info("Selecting run game screen");
        fxtools.moveToScreen(event, "/dev/aisandbox/client/fx/GameRun.fxml");
    }

    @FXML
    void editAgentAction(ActionEvent event) {
        // get the selected agent
        Agent agent = agentList.getSelectionModel().getSelectedItem();
        // show agent dialog
        editAgent(agent, ((Node) event.getTarget()).getScene().getWindow());
    }

    @FXML
    void clickAgentList(MouseEvent event) {
        // is this the second click
        if (event.getClickCount() == 2) {
            LOG.info("Double click on agent list");
            Agent agent = agentList.getSelectionModel().getSelectedItem();
            if (agent != null) {
                // edit agent
                editAgent(agent, ((Node) event.getTarget()).getScene().getWindow());
            }
        }
    }

    private void editAgent(Agent agent, Window window) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GameOptionsController.class.getResource("EditAgent.fxml"));
            VBox page = (VBox) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Agent");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            // link the controller with the correct agent
            EditAgentController c = loader.getController();
            c.assignAgent(agent);
            c.setDialogStage(dialogStage);
            // show dialog and wait until close
            dialogStage.showAndWait();
            // redraw the list just in case something has changed
            agentList.refresh();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error loading FXML", e);
        }
    }


    @FXML
    void initialize() {
        assert nextButton != null : "fx:id=\"nextButton\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert optionsBox != null : "fx:id=\"optionsBox\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert agentList != null : "fx:id=\"agentList\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert removeAgentButton != null : "fx:id=\"removeAgentButton\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert editAgentButton != null : "fx:id=\"editAgentButton\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert addAgentButton != null : "fx:id=\"addAgentButton\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert outputType != null : "fx:id=\"outputType\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert outputDirectory != null : "fx:id=\"outputDirectory\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert outputDirectoryButton != null : "fx:id=\"outputDirectoryButton\" was not injected: check your FXML file 'GameOptions.fxml'.";


        LOG.info("Registering property bindings");
        agentList.setItems(model.getAgentList());
        // disable remove agent if we dont have one selected
        removeAgentButton.disableProperty().bind(agentList.getSelectionModel().selectedItemProperty().isNull());
        // disable edit agent if we dont have one selected
        editAgentButton.disableProperty().bind(agentList.getSelectionModel().selectedItemProperty().isNull());
        // dont allow add agent if we already have enough agents
        IntegerBinding agentCount = Bindings.size(agentList.getItems());
        addAgentButton.disableProperty().bind(Bindings.greaterThanOrEqual(agentCount, model.getMaxAgents()));
        // dont allow the user to procede if the model isn't valid
        nextButton.disableProperty().bind(Bindings.not(model.getValid()));
        // set the agent formatting
        agentList.setCellFactory(new Callback<ListView<Agent>, ListCell<Agent>>() {
            @Override
            public ListCell<Agent> call(ListView<Agent> agentListView) {
                return new AgentCell();
            }
        });
    }


}
