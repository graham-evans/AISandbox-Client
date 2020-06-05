package dev.aisandbox.client;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.MP4Output;
import dev.aisandbox.client.output.NoOutput;
import dev.aisandbox.client.output.OutputFormat;
import dev.aisandbox.client.output.PNGOutputWriter;
import dev.aisandbox.client.profiler.AIProfiler;
import dev.aisandbox.client.scenarios.RuntimeResponse;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.SimulationException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The main (POJO) class used to hold the application state.
 *
 * <p>This uses the Lombok library to auto generate much of its functionality.
 *
 * <p>Directions
 *
 * <ol>
 *   <li>setup the public properties.
 *   <li>Call initialiseRuntime to setup the outputs.
 *   <li>Repeatedly call advance to run the simulation.
 *   <li>Call reset when finished to close the output files.
 * </ol>
 */
@Component
@Slf4j
public class ApplicationModel {

  /** The currently selected scenario to run. */
  @Getter Scenario scenario;

  @Getter BooleanProperty limitRuntime = new SimpleBooleanProperty(false);

  @Getter LongProperty maxStepCount = new SimpleLongProperty(100L);

  @Getter IntegerProperty minAgents = new SimpleIntegerProperty(1);

  @Getter IntegerProperty maxAgents = new SimpleIntegerProperty(1);

  @Getter ObservableList<Agent> agentList = FXCollections.observableList(new ArrayList<>());

  @Getter BooleanProperty valid = new SimpleBooleanProperty(false);

  @Getter @Setter private OutputFormat outputFormat = OutputFormat.NONE;

  @Getter @Setter private File outputDirectory = new File("./");

  @Setter long statsStepCount = -1; // how often should I save the stats

  private ScenarioRuntime runtime = null;
  private FrameOutput frameOutput = null;
  @Getter private GameRunController gameRunController = null;
  private long stepsTaken = 0;
  private AIProfiler profiler = null;
  private long nextProfileUpdate = 0;
  private File workingDirectory;

  /** Setup the model with useful default values. */
  public ApplicationModel() {
    // bind validation rules
    valid.bind(
        Bindings.and(
            Bindings.size(agentList).greaterThanOrEqualTo(minAgents),
            Bindings.size(agentList).lessThanOrEqualTo(maxAgents)));
    // make sure the fonts are loaded.
    Font.loadFont(ApplicationModel.class.getResourceAsStream("/fonts/Hack-Regular.ttf"), 12.0);
    Font.loadFont(ApplicationModel.class.getResourceAsStream("/fonts/OpenSans-Regular.ttf"), 12.0);
  }

  /**
   * Setter for the field <code>scenario</code>.
   *
   * <p>This updates the number of required agents based on the requested scenario
   *
   * @param s a {@link dev.aisandbox.client.scenarios.Scenario} object.
   */
  public void setScenario(Scenario s) {
    this.scenario = s;
    log.debug("changing scenario to {}", s.getName());
    minAgents.setValue(s.getMinAgentCount());
    maxAgents.setValue(s.getMaxAgentCount());
  }

  /**
   * Create and initialise runtime object.
   *
   * @param controller the UI to post results to.
   */
  public void initialiseRuntime(GameRunController controller) {
    // setup runtime
    runtime = scenario.getRuntime();
    runtime.setAgents(agentList);
    for (Agent a : agentList) {
      a.setupAgent();
    }
    runtime.initialise();
    // store controller
    this.gameRunController = controller;
    // setup output
    switch (outputFormat) {
      case PNG:
        frameOutput = new PNGOutputWriter();
        break;
      case MP4:
        frameOutput = new MP4Output();
        break;
      default: // no output
        frameOutput = new NoOutput();
    }
    // create a working directory & open output
    if ((outputFormat != OutputFormat.NONE) || (statsStepCount > -1)) {
      try {
        workingDirectory = createWorkingDirectory();
        frameOutput.open(workingDirectory);
      } catch (IOException e) {
        log.error("Error setting up output", e);
        controller.showSimulationError(new Exception("Error opening output."));
      }
    }
    // setup profiler
    profiler = new AIProfiler();
  }

  /**
   * Advance runtime one step and post the results to the UI and output.
   *
   * @throws SimulationException Thrown when the runtime cannot run the simulation.
   * @throws IOException Thrown when the output cannot be written or when there is a problem talking
   *     to the client {@link AgentException}.
   */
  public void advanceRuntime() throws SimulationException, IOException {
    try {
      RuntimeResponse response = runtime.advance();
      stepsTaken++;
      log.info("Recieved {} frame images", response.getImages().size());
      // show images
      for (BufferedImage image : response.getImages()) {
        frameOutput.addFrame(image);
        gameRunController.updateBoardImage(image);
      }
      // update profiler
      profiler.addProfileStep(response.getProfileStep());
      if (System.currentTimeMillis() > nextProfileUpdate) {
        gameRunController.updateProfileInformation(
            profiler.getChartImage(),
            profiler.getRunTime(),
            profiler.getAverateStepTime(),
            stepsTaken);
        // update profile in UI
        nextProfileUpdate = System.currentTimeMillis() + 1000 * 5;
      }
      // check for stats
      if ((statsStepCount > -1) && (stepsTaken % statsStepCount == 0)) {
        // write stats
        runtime.writeStatistics(new File(workingDirectory, Long.toString(stepsTaken) + ".csv"));
      }
    } catch (AgentException e) {
      log.error("Recieved exception from run");
      gameRunController.showAgentError(e);
      // rethrow so the running thread finishes
      throw new AgentException(e.getTarget(), e.getMessage());
    }
  }

  /** Close the output file (if any). */
  public void resetRuntime() {
    try {
      frameOutput.close();
    } catch (IOException e) {
      log.warn("Error closing frame output", e);
    }
  }

  private File createWorkingDirectory() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    File dir = new File(outputDirectory, "job-" + sdf.format(new Date()));
    dir.mkdirs();
    return dir;
  }
}
