package dev.aisandbox.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Launcher for the AI Sandbox Client.
 * <p>
 * This initialises Spring Boot, sets the FXML loader to use Spring then starts the JavaFX application.
 */
@SpringBootApplication
@Configuration
@ComponentScan
public class AISandboxClient extends Application {

    private static final Logger LOG = Logger.getLogger(AISandboxClient.class.getName());

    private ConfigurableApplicationContext context;
    private Parent rootNode;

    /**
     * The standard main method, that links to the JavaFX init method.
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(String[] args) {
        Application.launch(args);
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
        LOG.info("Initialising application");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AISandboxClient.class);
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
     * @param primaryStage the main (empty) window
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("Starting application");
        primaryStage.setScene(new Scene(rootNode, 800, 600));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * Used by JavaFX when closing the application.
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        LOG.info("Stopping application");
        context.close();
    }

}
