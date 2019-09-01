package dev.aisandbox.client.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class FXTools {

    private static final Logger LOG = Logger.getLogger(FXTools.class.getName());

    @Autowired
    private ApplicationContext appContext;

    public void moveToScreen(ActionEvent event, String fxml) {
        LOG.info("Selecting last screen");
        try {
            LOG.log(Level.INFO, "Selecting {} screen", fxml);
            FXMLLoader loader = new FXMLLoader(FXTools.class.getResource(fxml));
            loader.setResources(ResourceBundle.getBundle("dev.aisandbox.client.fx.UI"));
            loader.setControllerFactory(appContext::getBean);
            Scene s1 = ((Node) event.getSource()).getScene();
            Stage stage = (Stage) s1.getWindow();
            Scene s2 = new Scene(loader.load(), s1.getWidth(), s1.getHeight());
            stage.setScene(s2);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error switching Javafx scenes", e);
        }
    }
}
