package dev.aisandbox.client.fx;

import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.output.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
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
    private StackedBarChart<?, ?> responseGraph;

    @FXML
    private CategoryAxis responseChartXAxis;

    private Map<String, XYChart.Series<Long, Double>> responseSeries = new HashMap<>();

    @FXML
    private NumberAxis responseChartYAxis;

    @FXML
    private Button backButton;

    @FXML
    private Button startButton;

    @FXML
    private FlowPane imageAnchor;

    private ImageView imageView;

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
            // dicide which output class to use
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
        assert responseChartXAxis != null : "fx:id=\"responseChartXAxis\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert responseChartYAxis != null : "fx:id=\"responseChartYAxis\" was not injected: check your FXML file 'GameRun.fxml'.";
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
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(imageAnchor.heightProperty());
        imageView.fitWidthProperty().bind(imageAnchor.widthProperty());
        // setup response graph
        responseChartYAxis.setLabel("milliseconds");
    }

    /**
     * <p>addResponseChartCategory.</p>
     *
     * @param category a {@link java.lang.String} object.
     */
    public void addResponseChartCategory(String category) {
        XYChart.Series<Long, Double> series = new XYChart.Series<>();
        series.setName(category);
        responseSeries.put(category, series);
    }

    /**
     * <p>addResponseReading.</p>
     *
     * @param category a {@link java.lang.String} object.
     * @param step     a long.
     * @param reading  a double.
     */
    public void addResponseReading(String category, long step, double reading) {
        responseSeries.get(category).getData().add(new XYChart.Data(step, reading));
    }

    /**
     * Method to update the on-screen view of the simulation
     * <p>
     * This must only be called in the JavaFX thread (use Platform.runLater)
     *
     * @param image The pre-drawn {@link java.awt.image.BufferedImage} to display.
     */
    public void updateBoardImage(BufferedImage image) {
        // TODO convert this to run in any thread and skip frames if the UI can't keep up.
        imageView.setImage(SwingFXUtils.toFXImage(image, null));
    }
}
