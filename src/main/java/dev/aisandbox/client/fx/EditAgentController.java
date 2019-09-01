package dev.aisandbox.client.fx;

import dev.aisandbox.client.Agent;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditAgentController {

    private Agent agent;
    private static final Logger LOG = Logger.getLogger(EditAgentController.class.getName());

    @Setter
    Stage dialogStage;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField url;

    @FXML
    private RadioButton restXMLChoice;

    @FXML
    private RadioButton restJSONChoice;

    @FXML
    private CheckBox apiKey;

    @FXML
    private TextField apiKeyHeader;

    @FXML
    private TextField apiKeyValue;

    @FXML
    private CheckBox basicAuthentication;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void onCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void onSaveAction(ActionEvent event) {
        // TODO check target URL is valid
        // copy data from UI to agent
/*        try {
            agent.setTarget(new URL(url.getText()));
        } catch (MalformedURLException e) {
            LOG.log(Level.WARNING,"Error creating URL from user input",e);
            // TODO show error warning and revert to edit
        }

 */
        dialogStage.close();
    }

    public void assignAgent(Agent a) {
        this.agent = a;
        // copy the data to the UI
        url.setText(agent.getTarget().toString());
        // link the XML / JSON property
        restXMLChoice.selectedProperty().bindBidirectional(agent.getEnableXML());
        if (!agent.getEnableXML().getValue()) {
            restJSONChoice.selectedProperty().setValue(true);
        }
        // bind the rest of the controls
        apiKey.selectedProperty().bindBidirectional(agent.getEnableXML());
        apiKeyHeader.textProperty().bindBidirectional(agent.getApiKeyHeader());
        apiKeyValue.textProperty().bindBidirectional(agent.getApiKeyValue());
        basicAuthentication.selectedProperty().bindBidirectional(agent.getBasicAuth());
        username.textProperty().bindBidirectional(agent.getBasicAuthUsername());
        password.textProperty().bindBidirectional(agent.getBasicAuthPassword());
    }

    @FXML
    void initialize() {
        assert url != null : "fx:id=\"url\" was not injected: check your FXML file 'EditAgent.fxml'.";
        assert restXMLChoice != null : "fx:id=\"restXMLChoice\" was not injected: check your FXML file 'EditAgent.fxml'.";
        assert restJSONChoice != null : "fx:id=\"restJSONChoice\" was not injected: check your FXML file 'EditAgent.fxml'.";
        assert apiKey != null : "fx:id=\"apiKey\" was not injected: check your FXML file 'EditAgent.fxml'.";
        assert apiKeyHeader != null : "fx:id=\"apiKeyHeader\" was not injected: check your FXML file 'EditAgent.fxml'.";
        assert apiKeyValue != null : "fx:id=\"apiKeyValue\" was not injected: check your FXML file 'EditAgent.fxml'.";
        assert basicAuthentication != null : "fx:id=\"basicAuthentication\" was not injected: check your FXML file 'EditAgent.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'EditAgent.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'EditAgent.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'EditAgent.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'EditAgent.fxml'.";

        ToggleGroup xmlToggle = new ToggleGroup();
        restJSONChoice.setToggleGroup(xmlToggle);
        restXMLChoice.setToggleGroup(xmlToggle);

/*        if (restXMLChoice==null) {
            LOG.severe("restXMLChoice = null");
        }
        if (restXMLChoice.selectedProperty()==null) {
            LOG.severe("restXML.selectedProperty=null");
        }
  */  //
    }

}
