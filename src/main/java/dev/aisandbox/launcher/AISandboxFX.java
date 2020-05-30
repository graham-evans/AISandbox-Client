package dev.aisandbox.launcher;

import dev.aisandbox.client.ApplicationModel;
import dev.aisandbox.client.cli.CLIParser;
import dev.aisandbox.client.cli.PropertiesParser;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.cli.CommandLine;
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
 *
 * @author gde
 * @version $Id: $Id
 */
@SpringBootApplication(scanBasePackages = "dev.aisandbox.client")
public class AISandboxFX extends Application {

  private static final Logger LOG = LoggerFactory.getLogger(AISandboxFX.class);

  private ConfigurableApplicationContext context;
  private Parent rootNode;

  /**
   * {@inheritDoc}
   *
   * <p>Init method, sets up the Spring context and connects it to the FXMLLoader.
   */
  @Override
  public void init() throws Exception {
    LOG.info("Initialising application - FX");
    SpringApplicationBuilder builder = new SpringApplicationBuilder(AISandboxFX.class);
    builder.headless(false);
    context = builder.run(getParameters().getRaw().toArray(new String[0]));
    // parse parameters
    CommandLine cmd = CLIParser.parseOptions(getParameters().getRaw().toArray(new String[] {}));
    // check for debug
    if (cmd.hasOption(CLIParser.OPTION_DEBUG)) {
      CLIParser.enableDegug();
    }
    // check for lilith
    if (cmd.hasOption(CLIParser.OPTION_LILITH)) {
      CLIParser.enableLilith();
    }
    // check for config file
    if (cmd.hasOption(CLIParser.OPTION_CONFIG)) {
      // get the runtime model and properties parser from the spring context
      ApplicationModel model = context.getBean(ApplicationModel.class);
      PropertiesParser parser = context.getBean(PropertiesParser.class);
      parser.parseConfiguration(model, cmd.getOptionValue(CLIParser.OPTION_CONFIG));
    }
    // load the root FXML screen, using spring to create the controller
    FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/dev/aisandbox/client/fx/GameChoice.fxml"));
    loader.setResources(ResourceBundle.getBundle("dev.aisandbox.client.fx.UI"));
    loader.setControllerFactory(context::getBean);
    rootNode = loader.load();
  }

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
  @Override
  public void stop() throws Exception {
    LOG.info("Stopping application");
    ApplicationModel model = context.getBean(ApplicationModel.class);
    //    if (model.getScenario().isSimulationRunning()) {
    //      model.getScenario().stopSimulation();
    //    }
    context.close();
    System.exit(0);
  }
}
