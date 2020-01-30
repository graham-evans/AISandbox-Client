package dev.aisandbox.client;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.output.OutputFormat;
import dev.aisandbox.client.scenarios.Scenario;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The main (POJO) class used to hold the application state.
 *
 * <p>This uses the Lombok library to auto generate much of its functionality
 */
@Component
public class RuntimeModel {

  private static final Logger LOG = LoggerFactory.getLogger(RuntimeModel.class.getName());

  @Getter Scenario scenario;

  @Getter @Setter Locale locale = Locale.UK;

  @Getter BooleanProperty limitRuntime = new SimpleBooleanProperty(false);

  @Getter LongProperty maxStepCount = new SimpleLongProperty(100L);

  @Getter IntegerProperty minAgents = new SimpleIntegerProperty(1);

  @Getter IntegerProperty maxAgents = new SimpleIntegerProperty(1);

  @Getter ObservableList<Agent> agentList = FXCollections.observableList(new ArrayList<>());

  @Getter BooleanProperty valid = new SimpleBooleanProperty(false);

  @Getter @Setter private OutputFormat outputFormat = OutputFormat.NONE;

  @Getter @Setter private File outputDirectory = new File("./");

  /** Setup the model with usefull default values. */
  public RuntimeModel() {
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
    LOG.debug("changing scenario to {}", s.getName());
    minAgents.setValue(s.getMinAgentCount());
    maxAgents.setValue(s.getMaxAgentCount());
  }
}
