package dev.aisandbox.client;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import dev.aisandbox.client.cli.CLIParser;
import dev.aisandbox.client.cli.PropertiesParser;
import dev.aisandbox.client.fx.FakeGameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.NoOutput;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Launcher for the AI Sandbox Client.
 *
 * <p>This initialises Spring Boot, sets the FXML loader to use Spring then starts the JavaFX
 * application.
 */
@SpringBootApplication(scanBasePackages = "dev.aisandbox.client")
@Configuration
public class AISandboxClient extends Application {

  private static final Logger LOG = LoggerFactory.getLogger(AISandboxClient.class.getName());

  private ConfigurableApplicationContext context;
  private Parent rootNode;

  /**
   * The standard main method, that links to the JavaFX init method.
   *
   * @param args an array of {@link java.lang.String} objects.
   */
  public static void main(String[] args) {
    // get command line options
    Options options = CLIParser.getOptions();
    // parse them
    CommandLine cmd = null;
    try {
      CommandLineParser parser = new DefaultParser();
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      LOG.warn("Error parsing command line arguments");
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("java -jar AISandbox_<version>.jar", options);
      System.exit(-1);
    }
    // enable debug logs?
    if (cmd.hasOption(CLIParser.OPTION_DEBUG)) {
      enableDegug();
    }
    if (cmd.hasOption(CLIParser.OPTION_LILITH)) {
      enableLilith();
    }
    // get runtime model
    if (cmd.hasOption(CLIParser.OPTION_CONFIG)) {
      PropertiesParser parser = SpringContext.getBean(PropertiesParser.class);
      RuntimeModel model = SpringContext.getBean(RuntimeModel.class);
      parser.parseConfiguration(model, cmd.getOptionValue(CLIParser.OPTION_CONFIG));
      if (cmd.hasOption(CLIParser.OPTION_HEADLESS)) {
        // run the model headless
        runSimulationHeadless(model);
        System.exit(0);
      }
    }
    // initialise FX version
    Application.launch(args);
  }

  private static void enableDegug() {
    LOG.warn("Writing debug to file.");
    // Get the logback context
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    try {
      JoranConfigurator configurator = new JoranConfigurator();
      configurator.setContext(context);
      // Dont call reset - we want to add this configuration to the existing one
      configurator.doConfigure(AISandboxClient.class.getResourceAsStream("/logback-debug.xml"));
    } catch (JoranException je) {
      // StatusPrinter will handle this
    }
    LOG.info("Enabled logfile");
  }

  private static void enableLilith() {
    LOG.warn("Enabling lilith logging.");
    // Get the logback context
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    try {
      JoranConfigurator configurator = new JoranConfigurator();
      configurator.setContext(context);
      // Dont call reset - we want to add this configuration to the existing one
      configurator.doConfigure(AISandboxClient.class.getResourceAsStream("/logback-lilith.xml"));
    } catch (JoranException je) {
      // StatusPrinter will handle this
    }
    LOG.info("Enabled lilith logging");
  }

  protected static void runSimulationHeadless(RuntimeModel model) {
    LOG.info("Running in headless mode");
    if (model.getValid().get()) {
      // run the model manualy
      // TODO - create the correct output model
      FrameOutput out = new NoOutput();
      model
          .getScenario()
          .startSimulation(model.getAgentList(), new FakeGameRunController(), out, null);
    } else {
      LOG.info("Unable to run simulation - has it been fully configured?");
    }
  }

  /**
   * The JavaFX initialisation method.
   *
   * <p>This is called during the start process, and includes the setup of the Spring context.
   *
   * @throws Exception Thrown by the FX loader
   */
  @Override
  public void init() throws Exception {
    LOG.info("Initialising application");
    SpringApplicationBuilder builder = new SpringApplicationBuilder(AISandboxClient.class);
    builder.headless(false);
    context = builder.run(getParameters().getRaw().toArray(new String[0]));
    FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/dev/aisandbox/client/fx/GameChoice.fxml"));
    loader.setResources(ResourceBundle.getBundle("dev.aisandbox.client.fx.UI"));
    loader.setControllerFactory(context::getBean);
    rootNode = loader.load();
  }

  /**
   * JavaFX start method
   *
   * <p>Called after the init() method.
   *
   * <p>Opens the main window and centers on screen.
   *
   * @param primaryStage the main (empty) window
   * @throws Exception Throws by the FX loader
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    LOG.info("Starting application");
    primaryStage.setScene(new Scene(rootNode, 800, 600));
    primaryStage.centerOnScreen();
    primaryStage.setTitle("AI Sandbox");
    primaryStage
        .getIcons()
        .add(new Image(AISandboxClient.class.getResourceAsStream("fx/logo-small.png")));
    primaryStage.show();
  }

  /**
   * Used by JavaFX when closing the application.
   *
   * @throws Exception Thrown when closing the application
   */
  @Override
  public void stop() throws Exception {
    LOG.info("Stopping application");
    RuntimeModel model = context.getBean(RuntimeModel.class);
    model.getScenario().stopSimulation();
    context.close();
    System.exit(0);
  }
}
