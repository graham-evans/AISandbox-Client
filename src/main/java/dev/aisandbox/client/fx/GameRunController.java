package dev.aisandbox.client.fx;

import dev.aisandbox.client.*;
import dev.aisandbox.client.output.*;
import dev.aisandbox.client.profiler.AIProfiler;
import dev.aisandbox.client.profiler.ProfileStep;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javax.imageio.ImageIO;
import org.jfree.chart.fx.ChartViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/** Controller class used when running the simulations */
@Component
public class GameRunController {

  private static final Logger LOG = Logger.getLogger(GameRunController.class.getName());
  private final AtomicBoolean imageReady = new AtomicBoolean(true);
  @Autowired private ApplicationContext appContext;
  @Autowired private RuntimeModel model;
  @Autowired private FXTools fxtools;
  @FXML private ResourceBundle resources;
  @FXML private Pane durationChartPane;
  @FXML private Label runTimeField;
  @FXML private Label averageStepField;
  @FXML private Label stepCountField;
  @FXML private Button backButton;
  @FXML private Button startButton;
  @FXML private Pane imageAnchor;

  private AIProfiler profiler = null;
  private long profileLastUpdate = 0l;

  private ChartViewer durationChartViewer;
  private ImageView imageView;

  @FXML
  void backButtonAction(ActionEvent event) {
    LOG.info("Selecting options screen");
    fxtools.moveToScreen(event, "/dev/aisandbox/client/fx/GameOptions.fxml");
  }

  @FXML
  void startButtonAction(ActionEvent event) {
    if (model.getScenario().isSimulationRunning()) {
      stopSimulation();
    } else {
      startSimulation();
    }
  }

  private void startSimulation() {
    // reset the profiler
    profiler = new AIProfiler();
    // disable the back button
    backButton.setDisable(true);
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
    model
        .getScenario()
        .startSimulation(
            model.getAgentList(),
            this,
            out,
            model.getLimitRuntime().get() ? model.getMaxStepCount().get() : null);
    startButton.setText("Stop Simulation");
  }

  private void stopSimulation() {
    model.getScenario().stopSimulation();
    resetStartButton();
  }

  @FXML
  void initialize() {
    assert durationChartPane != null
        : "fx:id=\"durationGraph\" was not injected: check your FXML file 'GameRun.fxml'.";
    assert runTimeField != null
        : "fx:id=\"startTimeField\" was not injected: check your FXML file 'GameRun.fxml'.";
    assert averageStepField != null
        : "fx:id=\"runningField\" was not injected: check your FXML file 'GameRun.fxml'.";
    assert stepCountField != null
        : "fx:id=\"stepCountField\" was not injected: check your FXML file 'GameRun.fxml'.";
    assert backButton != null
        : "fx:id=\"backButton\" was not injected: check your FXML file 'GameRun.fxml'.";
    assert startButton != null
        : "fx:id=\"startButton\" was not injected: check your FXML file 'GameRun.fxml'.";
    assert imageAnchor != null
        : "fx:id=\"imageAnchor\" was not injected: check your FXML file 'GameRun.fxml'.";

    // setup autoscaling of imageview
    imageView = new ImageView();
    try {
      imageView.setImage(
          SwingFXUtils.toFXImage(
              ImageIO.read(
                  GameRunController.class.getResourceAsStream(
                      "/dev/aisandbox/client/testcard.png")),
              null));
    } catch (IOException e) {
      LOG.log(Level.SEVERE, "Error loading testcard", e);
    }
    imageAnchor.getChildren().add(imageView);
    // setup automatic resize
    imageAnchor
        .widthProperty()
        .addListener(
            (observableValue, number, t1) ->
                repositionImage(imageView, t1.doubleValue(), imageAnchor.getHeight()));
    imageAnchor
        .heightProperty()
        .addListener(
            (observableValue, number, t1) ->
                repositionImage(imageView, imageAnchor.getWidth(), t1.doubleValue()));

    // insert duration chart
    profiler = new AIProfiler();
    durationChartViewer = new ChartViewer(profiler.getChart());
    durationChartViewer.setMinSize(300.0, 200.0);
    durationChartViewer.setPrefSize(300.0, 200.0);
    durationChartViewer.setMaxSize(300.0, 200.0);
    durationChartPane.getChildren().add(durationChartViewer);
  }

  public void addProfileStep(ProfileStep step) {
    profiler.addProfileStep(step);
    // if it's been more than 2 seconds since the last update - redraw the state
    if (System.currentTimeMillis() - profileLastUpdate > 2000) {
      Platform.runLater(
          () -> {
            stepCountField.setText("Steps: " + profiler.getStepCount());
            durationChartViewer.setChart(profiler.getChart());
            runTimeField.setText("Run Time : " + FormatTools.formatTime(profiler.getRunTime()));
            averageStepField.setText(
                "Average Step : " + FormatTools.formatTime(profiler.getAverateStepTime()));
          });
      profileLastUpdate = System.currentTimeMillis();
    }
  }

  private void repositionImage(ImageView image, double paneWidth, double paneHeight) {
    // get image width and height
    double imageWidth = image.getImage().getWidth();
    double imageHeight = image.getImage().getHeight();
    LOG.log(
        Level.FINEST,
        "Scaling image {0}x{1} to pane {2}x{3}",
        new Object[] {imageWidth, imageHeight, paneWidth, paneHeight});
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
   * Method to update the on-screen view of the simulation
   *
   * <p>This can be called from any thread, but the screen will only update if the FX thread is not
   * busy.
   *
   * @param image The pre-drawn {@link java.awt.image.BufferedImage} to display.
   */
  public void updateBoardImage(BufferedImage image) {
    if (imageReady.getAndSet(false)) {
      Platform.runLater(
          () -> {
            imageView.setImage(SwingFXUtils.toFXImage(image, null));
            imageReady.set(true);
          });
    }
  }

  public void resetStartButton() {
    Platform.runLater(
        () -> {
          startButton.setText("Start Simulation");
          backButton.setDisable(false);
        });
  }

  public void showAgentError(String agentURL, Exception e) {
    // special case - is this an agent excetpion
    if (e instanceof AgentConnectionException) {
      showAgentError(agentURL, "Error connecting to agent", e.getMessage());
    } else if (e instanceof AgentParserException) {
      AgentParserException ape = (AgentParserException) e;
      StringBuilder sb = new StringBuilder();
      sb.append("Connection to the server produced HTTP code ");
      sb.append(ape.getResponseCode());
      sb.append("\nResponse:\n");
      sb.append(ape.getResponse());
      showAgentError(agentURL, "Error parsing server response", sb.toString());
    } else {
      // show generic exception
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      // pass this as the details
      showAgentError(agentURL, "Agent Exception", sw.toString());
    }
  }

  public void showAgentError(String agentURL, String description, String details) {
    Platform.runLater(
        () -> {
          // show the exception
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Agent Error");
          alert.setHeaderText("There was an error talking to an agent");
          alert.setContentText(agentURL);

          Label label = new Label(description);

          TextArea textArea = new TextArea(details);
          textArea.setEditable(false);
          textArea.setWrapText(true);

          textArea.setMaxWidth(Double.MAX_VALUE);
          textArea.setMaxHeight(Double.MAX_VALUE);
          GridPane.setVgrow(textArea, Priority.ALWAYS);
          GridPane.setHgrow(textArea, Priority.ALWAYS);

          GridPane expContent = new GridPane();
          expContent.setMaxWidth(Double.MAX_VALUE);
          expContent.add(label, 0, 0);
          expContent.add(textArea, 0, 1);

          // Set expandable Exception into the dialog pane.
          alert.getDialogPane().setExpandableContent(expContent);

          alert.showAndWait();
        });
  }
}
