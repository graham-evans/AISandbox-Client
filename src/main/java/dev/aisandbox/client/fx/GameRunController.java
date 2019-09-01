package dev.aisandbox.client.fx;

import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.output.MP4Output;
import dev.aisandbox.client.output.OutputTools;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class GameRunController {

    private static final Logger LOG = Logger.getLogger(GameRunController.class.getName());

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    RuntimeModel model;

    @Autowired
    FXTools fxtools;

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
    private ScrollPane imageScrollPane;

    @FXML
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
            model.getScenario().startSimulation(model.getAgentList(), this, new MP4Output());
            startButton.setText("Stop Simulation");
        }
    }

    @FXML
    void initialize() {
        assert imageScrollPane != null : "fx:id=\"imageScrollPane\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert rewardGraph != null : "fx:id=\"rewardGraph\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert responseGraph != null : "fx:id=\"responseGraph\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert responseChartXAxis != null : "fx:id=\"responseChartXAxis\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert responseChartYAxis != null : "fx:id=\"responseChartYAxis\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'GameRun.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'GameRun.fxml'.";

        // setup autoscaling of imageview

        DoubleProperty vscale = new SimpleDoubleProperty();
        vscale.bind(imageScrollPane.heightProperty().divide(OutputTools.VIDEO_HEIGHT));
        DoubleProperty hscale = new SimpleDoubleProperty();
        hscale.bind(imageScrollPane.widthProperty().divide(OutputTools.VIDEO_WIDTH));
        DoubleProperty scale = new SimpleDoubleProperty();
        scale.bind(Bindings.min(vscale, hscale));

        imageView.fitHeightProperty().bind(scale.multiply(OutputTools.VIDEO_HEIGHT));
        imageView.fitWidthProperty().bind(scale.multiply(OutputTools.VIDEO_WIDTH));

        // add logging
        scale.addListener((observable, oldValue, newValue) -> {
            LOG.info("===");
            LOG.info("PaneWidth=" + imageScrollPane.getWidth());
            LOG.info("PaneHeight=" + imageScrollPane.getHeight());
            LOG.info("VScale= " + vscale.get());
            LOG.info("HScale=" + hscale.get());
            LOG.info("Scale=" + scale.get());
            LOG.info("ImageWidth=" + imageView.fitWidthProperty().get());
            LOG.info("ImageHeight=" + imageView.fitHeightProperty().get());
//               imageView.setFitHeight(scale.get() * OutputTools.VIDEO_HEIGHT);
//               imageView.setFitWidth(scale.get()*OutputTools.VIDEO_WIDTH);
        });
//        imageAnchorPane.widthProperty().addListener(new LoggingChangeListener("Pane Width"));
//        imageAnchorPane.heightProperty().addListener(new LoggingChangeListener("Pane Height"));
//           vscale.addListener(new LoggingChangeListener("VScale"));
//           hscale.addListener(new LoggingChangeListener("HScale"));
//           scale.addListener(new LoggingChangeListener("scale"));

        // setup response graph
        responseChartYAxis.setLabel("milliseconds");
    }

    public void addResponseChartCategory(String category) {
        XYChart.Series<Long, Double> series = new XYChart.Series<>();
        series.setName(category);
        responseSeries.put(category, series);
    }

    public void addResponseReading(String category, long step, double reading) {
        responseSeries.get(category).getData().add(new XYChart.Data(step, reading));
    }

    /**
     * This must only be called in the JavaFX thread (use Platform.runLater)
     *
     * @param image
     */
    public void updateBoardImage(BufferedImage image) {
        // TODO convert this to run in any thread and skip frames if the UI can't keey up.
        imageView.setImage(SwingFXUtils.toFXImage(image, null));
    }
}
