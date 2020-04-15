package dev.aisandbox.client.fx;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * FXTools class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
@Slf4j
public class FXTools {

  @Autowired private ApplicationContext appContext;

  /**
   * moveToScreen.
   *
   * @param event a {@link javafx.event.ActionEvent} object.
   * @param fxml a {@link java.lang.String} object.
   */
  public void moveToScreen(ActionEvent event, String fxml) {
    try {
      log.debug("Selecting {} screen", fxml);
      FXMLLoader loader = new FXMLLoader(FXTools.class.getResource(fxml));
      loader.setResources(ResourceBundle.getBundle("dev.aisandbox.client.fx.UI"));
      loader.setControllerFactory(appContext::getBean);
      Scene s1 = ((Node) event.getSource()).getScene();
      Stage stage = (Stage) s1.getWindow();
      Scene s2 = new Scene(loader.load(), s1.getWidth(), s1.getHeight());
      stage.setScene(s2);
    } catch (IOException e) {
      log.error("Error switching Javafx scenes", e);
    }
  }
}
