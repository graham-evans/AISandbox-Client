package dev.aisandbox.client.fx;

import com.dooapp.fxform.FXForm;
import com.dooapp.fxform.view.FXFormSkinFactory;
import dev.aisandbox.client.OutputFormat;
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
import javafx.stage.*;
import dev.aisandbox.client.Agent;
import dev.aisandbox.client.RuntimeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>GameOptionsController class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
public class GameOptionsController {

    private static final Logger LOG = Logger.getLogger(GameOptionsController.class.getName());

    @Autowired
    private RuntimeModel model;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private FXTools fxtools;

    @FXML
    private ResourceBundle resources;

    @FXML
    private ScrollPane optionPane;

    @FXML
    private ListView<Agent> agentList;

    @FXML
    private Button removeAgentButton;

    @FXML
    private Button editAgentButton;

    @FXML
    private Button addAgentButton;

    @FXML
    private ChoiceBox<OutputFormat> outputFormat;

    @FXML
    private TextField outputDirectory;

    @FXML
    private Button outputDirectoryButton;

    @FXML
    private Button nextButton;

    @FXML
    void removeAgentEvent(ActionEvent event) {
        // get the selected agent
        Agent agent = agentList.getSelectionModel().getSelectedItem();
        LOG.log(Level.INFO, "Removing agent {0}", agent);
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
            loader.setResources(ResourceBundle.getBundle("dev.aisandbox.client.fx.UI"));
            VBox page = loader.load();
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
    void chooseOutputDirectory(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        // try and populate the directory from the existing text
        try {
            File f = new File(outputDirectory.getText());
            if (f.isDirectory()) {
                dc.setInitialDirectory(f);
            }
        } catch (Exception e) {
            LOG.log(Level.FINEST, "Invalid directory in text field", e);
        }
        File fout = dc.showDialog(((Node) event.getTarget()).getScene().getWindow());
        if ((fout != null) && fout.isDirectory()) {
            model.setOutputDirectory(fout);
            outputDirectory.setText(fout.getAbsolutePath());
        }

    }

    @FXML
    void initialize() {
        assert optionPane != null : "fx:id=\"optionPane\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert agentList != null : "fx:id=\"agentList\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert removeAgentButton != null : "fx:id=\"removeAgentButton\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert editAgentButton != null : "fx:id=\"editAgentButton\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert addAgentButton != null : "fx:id=\"addAgentButton\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert outputFormat != null : "fx:id=\"outputFormat\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert outputDirectory != null : "fx:id=\"outputDirectory\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert outputDirectoryButton != null : "fx:id=\"outputDirectoryButton\" was not injected: check your FXML file 'GameOptions.fxml'.";
        assert nextButton != null : "fx:id=\"nextButton\" was not injected: check your FXML file 'GameOptions.fxml'.";

        LOG.info("Adding scenario options");
        FXForm options = new FXForm(model.getScenario());
        options.setSkin(FXFormSkinFactory.INLINE_FACTORY.createSkin(options));
        optionPane.setContent(options);

        outputFormat.getItems().setAll(OutputFormat.values());
        outputFormat.getSelectionModel().select(model.getOutputFormat());
        outputFormat.setOnAction(e -> model.setOutputFormat(outputFormat.getSelectionModel().getSelectedItem()));
        if (model.getOutputDirectory() != null) {
            outputDirectory.setText(model.getOutputDirectory().getAbsolutePath());
        } else {
            outputDirectory.setText("");
        }
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
        agentList.setCellFactory(agentListView -> new AgentCell());
    }


}
