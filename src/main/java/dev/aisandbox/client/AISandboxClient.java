package dev.aisandbox.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import dev.aisandbox.client.cli.CLIParser;
import dev.aisandbox.client.fx.FakeGameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.NoOutput;

import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Launcher for the AI Sandbox Client.
 * <p>
 * This initialises Spring Boot, sets the FXML loader to use Spring then starts the JavaFX application.
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
        if ((args != null) && (args.length > 0)) {
            // initialise CLI version
            AISandboxClient client = new AISandboxClient();
            client.runCLI(args);
        } else {
            // initialise FX version
            Application.launch(args);
        }
    }

    public void runCLI(String[] args) {
        LOG.info("CLI activated");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AISandboxClient.class);
        builder.headless(true);
        context = builder.run(args);
        // generate the default model and update with CLI (and XML) options
        CLIParser cli = context.getBean(CLIParser.class);
        RuntimeModel model = cli.parseCommandLine(context.getBean(RuntimeModel.class), args);
        if (model.getValid().get()) {
            // run the model manualy
            FrameOutput out = new NoOutput();
            model.getScenario().startSimulation(model.getAgentList(), new FakeGameRunController(), out, null);
        } else {
            cli.printHelp();
        }
    }

    /**
     * The JavaFX initialisation method.
     * <p>
     * This is called during the start process, and includes the setup of the Spring context.
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AISandboxClient.class);
        builder.headless(false);
        context = builder.run(getParameters().getRaw().toArray(new String[0]));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/aisandbox/client/fx/GameChoice.fxml"));
        loader.setResources(ResourceBundle.getBundle("dev.aisandbox.client.fx.UI"));
        loader.setControllerFactory(context::getBean);
        rootNode = loader.load();
    }

    /**
     * JavaFX start method
     * <p>
     * Called after the init() method.
     * <p>
     * Opens the main window and centers on screen.
     *
     * @param primaryStage the main (empty) window
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("Starting application");
        primaryStage.setScene(new Scene(rootNode, 800, 600));
        primaryStage.centerOnScreen();
        primaryStage.setTitle("AI Sandbox");
        primaryStage.getIcons().add(new Image(AISandboxClient.class.getResourceAsStream("fx/logo-small.png")));
        primaryStage.show();
    }

    /**
     * Used by JavaFX when closing the application.
     *
     * @throws Exception
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
