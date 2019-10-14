package dev.aisandbox.client.fx;

import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.output.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class used when running the simulations
 */
@Component
public class GameRunController {

    private static final Logger LOG = Logger.getLogger(GameRunController.class.getName());

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private RuntimeModel model;

    @Autowired
    private FXTools fxtools;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private LineChart<?, ?> rewardGraph;

    @FXML
    private StackedAreaChart<?, ?> responseGraph;

    @FXML
    private Button backButton;

    @FXML
    private Button startButton;

    @FXML
    private Pane imageAnchor;

    private ImageView imageView;

    private StackedAreaChartController timingsController;

    @FXML
    void backButtonAction(ActionEvent event) {
        LOG.info("Selecting options screen");
        fxtools.moveToScreen(event, "/dev/aisandbox/client/fx/GameOptions.fxml");
    }

    @FXML
    void startButtonAction(ActionEvent event) {
        if (model.getScenario().isSimulationRunning()) {
            model.getScenario().stopSimulation();
            startButton.setText("Start Simulation");
        } else {
            // reset the charts
            timingsController.reset();
            // decide which output class to use
            FrameOutput out;
            switch (model.getOutputFormat()) {
                case MP4:
                    out = new MP4Output();
                    break;
                case PNG:
                    out = new PNGOutputWriter();
                    break;
                default:
                    out = new NoOutput();
            }
            // setup output
            try {
                out.open(model.getOutputDirectory());
            } catch (IOException e) {
                LOG.log(Level.WARNING, "Error opening output", e);
            }
            model.getScenario().startSimulation(model.getAgentList(), this, out);
            startButton.setText("Stop Simulation");
        }
    }

    @FXML
    void initialize() {
        assert imageAnchor != null : "fx:id=\"imageAnchor\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert rewardGraph != null : "fx:id=\"rewardGraph\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert responseGraph != null : "fx:id=\"responseGraph\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'GameRun.fxml'.";
        // setup autoscaling of imageview
        imageView = new ImageView();
        try {
            imageView.setImage(SwingFXUtils.toFXImage(ImageIO.read(GameRunController.class.getResourceAsStream("/dev/aisandbox/client/testcard.png")), null));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error loading testcard", e);
        }
        imageAnchor.getChildren().add(imageView);
        // setup automatic resize
        imageAnchor.widthProperty().addListener((observableValue, number, t1) ->
                repositionImage(imageView, t1.doubleValue(), imageAnchor.getHeight())
        );
        imageAnchor.heightProperty().addListener((observableValue, number, t1) ->
                repositionImage(imageView, imageAnchor.getWidth(), t1.doubleValue())
        );
        // setup response graph
        responseGraph.getYAxis().setLabel("milliseconds");

        timingsController = new StackedAreaChartController(responseGraph);

    }

    private void repositionImage(ImageView image, double paneWidth, double paneHeight) {
        // get image width and height
        double imageWidth = image.getImage().getWidth();
        double imageHeight = image.getImage().getHeight();
        LOG.log(Level.FINEST, "Scaling image {0}x{1} to pane {2}x{3}", new Object[]{imageWidth, imageHeight, paneWidth, paneHeight});
        // work out the best scale
        double scaleX = paneWidth / imageWidth;
        double scaleY = paneHeight / imageHeight;
        double scale = Math.min(scaleX, scaleY);
        image.setFitWidth(scale * imageWidth);
        image.setFitHeight(scale * imageHeight);
        image.setX((paneWidth - scale * imageWidth) / 2.0);
        image.setY((paneHeight - scale * imageHeight) / 2.0);
    }

    /**
     * Update the timings chart with new entries
     * <p>
     * Pass a Map (usualy a TreeMap) of name:value pairs which will be added to the chart.
     * If there are more than 20 entries already - the oldest one should be removed.
     * @param timings
     */
    public void addResponseTimings(Map<String, Double> timings) {
        Platform.runLater(() ->
                timingsController.add(timings)
        );
    }

    private final AtomicBoolean imageReady = new AtomicBoolean(true);
    /**
     * Method to update the on-screen view of the simulation
     * <p>
     * This can be called from any thread, but the screen will only update if the FX thread is not busy.
     *
     * @param image The pre-drawn {@link java.awt.image.BufferedImage} to display.
     */
    public void updateBoardImage(BufferedImage image) {
        if (imageReady.getAndSet(false)) {
            Platform.runLater(() -> {
                imageView.setImage(SwingFXUtils.toFXImage(image, null));
                imageReady.set(true);
            });
        }
    }
}
