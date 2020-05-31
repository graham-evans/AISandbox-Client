package dev.aisandbox.client;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.NoOutput;
import dev.aisandbox.client.output.OutputFormat;
import dev.aisandbox.client.profiler.AIProfiler;
import dev.aisandbox.client.scenarios.RuntimeResponse;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.SimulationException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

  /** The currently selected scenario to run */
  @Getter Scenario scenario;

  @Getter BooleanProperty limitRuntime = new SimpleBooleanProperty(false);

  @Getter LongProperty maxStepCount = new SimpleLongProperty(100L);

  @Getter IntegerProperty minAgents = new SimpleIntegerProperty(1);

  @Getter IntegerProperty maxAgents = new SimpleIntegerProperty(1);

  @Getter ObservableList<Agent> agentList = FXCollections.observableList(new ArrayList<>());

  @Getter BooleanProperty valid = new SimpleBooleanProperty(false);

  @Getter @Setter private OutputFormat outputFormat = OutputFormat.NONE;

  @Getter @Setter private File outputDirectory = new File("./");

  private ScenarioRuntime runtime = null;
  private FrameOutput frameOutput = null;
  @Getter private GameRunController gameRunController = null;
  private long stepsTaken = 0;
  private AIProfiler profiler = null;

  /** Setup the model with useful default values. */
  public ApplicationModel() {
    // TODO - load default values

    // bind validation rules
    valid.bind(
        Bindings.and(
            Bindings.size(agentList).greaterThanOrEqualTo(minAgents),
            Bindings.size(agentList).lessThanOrEqualTo(maxAgents)));
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
    // TODO - open output
    frameOutput = new NoOutput();
    // TODO - open stats
    // setup profiler
    profiler = new AIProfiler();
  }

  public void advanceRuntime() throws AgentException, SimulationException, IOException {
    try {
      RuntimeResponse response = runtime.advance();
      log.info("Recieved {} frame images", response.getImages().size());
      // show images
      for (BufferedImage image : response.getImages()) {
        frameOutput.addFrame(image);
        gameRunController.updateBoardImage(image);
      }
      // update profiler
      if (response.getProfileStep() != null) {
        profiler.addProfileStep(response.getProfileStep());
      }
    } catch (AgentException e) {
      // TODO show exceptions to UI

    }
    // TODO update stats
  }

  public void resetRuntime() throws IOException {
    frameOutput.close();
  }
}
