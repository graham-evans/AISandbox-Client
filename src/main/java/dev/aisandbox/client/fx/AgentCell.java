package dev.aisandbox.client.fx;

import dev.aisandbox.client.agent.Agent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.glyphfont.Glyph;

/**
 * AgentCell class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
public class AgentCell extends ListCell<Agent> {

  @FXML private Label protocolLabel;

  @FXML private Label urlLabel;

  @FXML private Glyph padlock;

  @FXML private HBox root;

  private FXMLLoader mLLoader = null;

  /** {@inheritDoc} */
  @Override
  protected void updateItem(Agent agent, boolean empty) {
    super.updateItem(agent, empty);

    if (empty || agent == null) {

      setText(null);
      setGraphic(null);

    } else {
      if (mLLoader == null) {
        mLLoader =
            new FXMLLoader(getClass().getResource("/dev/aisandbox/client/fx/AgentCell.fxml"));
        mLLoader.setController(this);

        try {
          mLLoader.load();
        } catch (IOException e) {
          log.error("Error loading FXML", e);
        }
      }
      // show red URLs when invalid
      if (agent.getValidProperty().get()) {
        urlLabel.getStyleClass().remove("error");
      } else {
        urlLabel.getStyleClass().add("error");
      }
      padlock.setColor(Color.DARKGRAY);
      if (agent.isEnableXML()) {
        protocolLabel.setText("<>");
      } else {
        protocolLabel.setText("{}");
      }

      urlLabel.setText(agent.getTarget());

      setText(null);
      setGraphic(root);
    }
  }
}
