package dev.aisandbox.client.fx;

import dev.aisandbox.client.agent.Agent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EditAgentController class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class EditAgentController {

  private Agent agent;
  private static final Logger LOG = LoggerFactory.getLogger(EditAgentController.class.getName());

  @Setter Stage dialogStage;

  @FXML private ResourceBundle resources;

  @FXML private URL location;

  @FXML private TextField url;

  @FXML private RadioButton restXMLChoice;

  @FXML private RadioButton restJSONChoice;

  @FXML private CheckBox apiKey;

  @FXML private TextField apiKeyHeader;

  @FXML private TextField apiKeyValue;

  @FXML private CheckBox basicAuthentication;

  @FXML private TextField username;

  @FXML private PasswordField password;

  @FXML private Button saveButton;

  @FXML private Button cancelButton;

  @FXML
  void onCancelAction(ActionEvent event) {
    LOG.info("Closing without saving");
    dialogStage.close();
  }

  @FXML
  void onSaveAction(ActionEvent event) {
    LOG.info("Saving agent info");
    // TODO check target URL is valid
    // copy data from UI to agent
    agent.setTarget(url.getText());
    agent.setEnableXML(restXMLChoice.isSelected());
    agent.setBasicAuth(basicAuthentication.isSelected());
    agent.setBasicAuthUsername(username.getText());
    agent.setBasicAuthPassword(password.getText());
    agent.setApiKey(apiKey.isSelected());
    agent.setApiKeyHeader(apiKeyHeader.getText());
    agent.setApiKeyValue(apiKeyValue.getText());
    // close dialog
    dialogStage.close();
  }

  /**
   * Copy the agent information to the controller.
   *
   * @param a Agent
   */
  public void assignAgent(Agent a) {
    /*
     * NOTE - we copy the data rather than bind, so we can use a cancel button (which leaves the
     * original agent unchanged).
     */
    this.agent = a;
    // copy the data to the UI
    url.setText(agent.getTarget());
    // link the XML / JSON property
    if (agent.isEnableXML()) {
      restXMLChoice.selectedProperty().set(true);
    } else {
      restJSONChoice.selectedProperty().set(true);
    }
    // basic auth
    basicAuthentication.selectedProperty().set(agent.isBasicAuth());
    username.setText(agent.getBasicAuthUsername());
    password.setText(agent.getBasicAuthPassword());
    // API Key
    apiKey.selectedProperty().set(agent.isApiKey());
    apiKeyHeader.setText(agent.getApiKeyHeader());
    apiKeyValue.setText(agent.getApiKeyValue());
  }

  @FXML
  void initialize() {
    assert url != null : "fx:id=\"url\" was not injected: check your FXML file 'EditAgent.fxml'.";
    assert restXMLChoice != null
        : "fx:id=\"restXMLChoice\" was not injected: check your FXML file 'EditAgent.fxml'.";
    assert restJSONChoice != null
        : "fx:id=\"restJSONChoice\" was not injected: check your FXML file 'EditAgent.fxml'.";
    assert apiKey != null
        : "fx:id=\"apiKey\" was not injected: check your FXML file 'EditAgent.fxml'.";
    assert apiKeyHeader != null
        : "fx:id=\"apiKeyHeader\" was not injected: check your FXML file 'EditAgent.fxml'.";
    assert apiKeyValue != null
        : "fx:id=\"apiKeyValue\" was not injected: check your FXML file 'EditAgent.fxml'.";
    assert basicAuthentication != null
        : "fx:id=\"basicAuthentication\" was not injected: check your FXML file 'EditAgent.fxml'.";
    assert username != null
        : "fx:id=\"username\" was not injected: check your FXML file 'EditAgent.fxml'.";
    assert password != null
        : "fx:id=\"password\" was not injected: check your FXML file 'EditAgent.fxml'.";
    assert saveButton != null
        : "fx:id=\"saveButton\" was not injected: check your FXML file 'EditAgent.fxml'.";
    assert cancelButton != null
        : "fx:id=\"cancelButton\" was not injected: check your FXML file 'EditAgent.fxml'.";

    ToggleGroup xmlToggle = new ToggleGroup();
    restJSONChoice.setToggleGroup(xmlToggle);
    restXMLChoice.setToggleGroup(xmlToggle);
  }
}
