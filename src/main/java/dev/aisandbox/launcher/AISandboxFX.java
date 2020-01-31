package dev.aisandbox.launcher;

import dev.aisandbox.client.RuntimeModel;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Spring Boot / JavaFX version of the application launcher.
 *
 * <p>The Launcher will
 *
 * <ol>
 *   <li>Parse any command line options
 *   <li>Read any properties files
 *   <li>Load the first FX scene
 * </ol>
 */
@SpringBootApplication(scanBasePackages = "dev.aisandbox.client")
public class AISandboxFX extends Application {

  private static final Logger LOG = LoggerFactory.getLogger(AISandboxFX.class);

  private ConfigurableApplicationContext context;
  private Parent rootNode;

  /**
   * Init method, sets up the Spring context and connects it to the FXMLLoader
   *
   * @throws Exception Exeption setting up the FX app
   */
  @Override
  public void init() throws Exception {
    LOG.info("Initialising application - FX");
    SpringApplicationBuilder builder = new SpringApplicationBuilder(AISandboxFX.class);
    builder.headless(false);
    context = builder.run(getParameters().getRaw().toArray(new String[0]));
    FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/dev/aisandbox/client/fx/GameChoice.fxml"));
    loader.setResources(ResourceBundle.getBundle("dev.aisandbox.client.fx.UI"));
    loader.setControllerFactory(context::getBean);
    rootNode = loader.load();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    LOG.info("Starting application - FX");
    primaryStage.setScene(new Scene(rootNode, 800, 600));
    primaryStage.centerOnScreen();
    primaryStage.setTitle("AI Sandbox");
    primaryStage
        .getIcons()
        .add(
            new Image(
                AISandboxFX.class.getResourceAsStream("/dev/aisandbox/client/fx/logo-small.png")));
    primaryStage.show();
  }

  @Override
  public void stop() throws Exception {
    LOG.info("Stopping application");
    RuntimeModel model = context.getBean(RuntimeModel.class);
    model.getScenario().stopSimulation();
    context.close();
    System.exit(0);
  }
}
