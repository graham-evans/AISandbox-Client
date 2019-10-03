package dev.aisandbox.client.fx;

import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.output.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    private Pane imageAnchor;

    //    @FXML
//    private ImageView imageView;
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
//        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'GameRun.fxml'.";
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
//        imageView.xProperty().bind(imageAnchor.widthProperty().add(imageView.fitWidthProperty().multiply(-1)).divide(2));

        // TODO - View https://stackoverflow.com/questions/12630296/resizing-images-to-fit-the-parent-node

        /*
        DoubleProperty vscale = new SimpleDoubleProperty();
        vscale.bind(imageAnchor.heightProperty().divide(OutputTools.VIDEO_HEIGHT));
        DoubleProperty hscale = new SimpleDoubleProperty();
        hscale.bind(imageAnchor.widthProperty().divide(OutputTools.VIDEO_WIDTH));
        DoubleProperty scale = new SimpleDoubleProperty();
        scale.bind(Bindings.min(vscale, hscale));
         */
//        imageView.fitHeightProperty().bind(scale.multiply(OutputTools.VIDEO_HEIGHT));
//        imageView.fitWidthProperty().bind(scale.multiply(OutputTools.VIDEO_WIDTH));
/*
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(imageAnchor.widthProperty());
        imageView.fitHeightProperty().bind(imageAnchor.heightProperty());
*/
        // add logging
        imageAnchor.heightProperty().addListener((observable, oldValue, newValue) -> {
            LOG.info("===");
            LOG.info("PaneWidth=" + imageAnchor.getWidth());
            LOG.info("PaneHeight=" + imageAnchor.getHeight());
            LOG.info("ImageWidth=" + imageView.getFitWidth());
            LOG.info("ImageHeight=" + imageView.getFitHeight());
        });

//               imageView.setFitHeight(scale.get() * OutputTools.VIDEO_HEIGHT);
//               imageView.setFitWidth(scale.get()*OutputTools.VIDEO_WIDTH);
//        });
//        imageAnchorPane.widthProperty().addListener(new LoggingChangeListener("Pane Width"));
//        imageAnchorPane.heightProperty().addListener(new LoggingChangeListener("Pane Height"));
//           vscale.addListener(new LoggingChangeListener("VScale"));
//           hscale.addListener(new LoggingChangeListener("HScale"));
//           scale.addListener(new LoggingChangeListener("scale"));

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
        // TODO convert this to run in any thread and skip frames if the UI can't keey up.
        imageView.setImage(SwingFXUtils.toFXImage(image, null));
    }
}
