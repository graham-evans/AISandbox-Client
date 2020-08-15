package dev.aisandbox.client.cli;

import dev.aisandbox.client.ApplicationModel;
import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.output.OutputFormat;
import dev.aisandbox.client.parameters.ParameterParseException;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PropertiesParser class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
@Slf4j
public class PropertiesParser {

  private final List<Scenario> scenarioList;

  @Autowired
  public PropertiesParser(List<Scenario> scenarioList) {
    this.scenarioList = scenarioList;
  }

  /**
   * Parse a configuration file, updateing the RuntimeModel to match.
   *
   * @param model The existing runtime model
   * @param filePath a reference to the file to be parsed
   * @return the (updated) runtime model
   */
  public ApplicationModel parseConfiguration(ApplicationModel model, String filePath) {
    // read properties from file
    File pfile = new File(filePath);
    log.debug("Loading properties from {}", pfile.getAbsolutePath());
    Properties props = new Properties();
    try {
      props.load(new FileInputStream(pfile));
    } catch (IOException e) {
      log.warn("Error loading properties", e);
    }
    // parse and return
    return parseConfiguration(model, props);
  }

  /**
   * Parse a properties object and update the runtime model to match.
   *
   * @param model The runtime model to update
   * @param props A properties object
   * @return the (updated) runtime model
   */
  public ApplicationModel parseConfiguration(ApplicationModel model, Properties props) {
    // read scenario specific properties
    String scenario = props.getProperty("scenario");
    // use the correct scenario
    for (Scenario s : scenarioList) {
      if (s.getId().equalsIgnoreCase(scenario)) {
        model.setScenario(s);
      }
    }
    // check a scenario has been loaded
    if (model.getScenario() == null) {
      log.info("No scenario selected from properties");
    } else {
      // look at the list of parameters
      for (ScenarioParameter param : model.getScenario().getParameterArray()) {
        log.debug("Looking for parameter '{}'", param.getParameterKey());
        String value = props.getProperty(param.getParameterKey());
        if (value != null) {
          try {
            param.setParsableValue(value);
          } catch (ParameterParseException e) {
            log.warn("Error parsing parameter '{}' - '{}'", param.getParameterKey(), value);
          }
        }
      }
    }
    readGeneralSettings(model, props);
    readAgentSettings(model, props);
    return model;
  }

  /**
   * Parse a properties object for settings related to agents.
   *
   * @param model the runtime model
   * @param props the properties object to scan
   */
  public void readAgentSettings(ApplicationModel model, Properties props) {
    if (props.containsKey("agents")) {
      int agentCount = Integer.parseInt(props.getProperty("agents"));
      for (int i = 1; i <= agentCount; i++) {
        Agent agent = new Agent();
        if (props.containsKey("agent" + i + "URL")) {
          agent.setTarget(props.getProperty("agent" + i + "URL"));
        }
        if ("XML"
            .equals(
                props.getProperty(
                    "agent" + i + "Lang"))) { // we only test for XML as JSON is the default
          agent.setEnableXML(true);
        }
        if (props.containsKey("agent" + i + "Username")) {
          agent.setBasicAuth(true);
          agent.setBasicAuthUsername(props.getProperty("agent" + i + "Username"));
          agent.setBasicAuthPassword(props.getProperty("agent" + i + "Password"));
        }
        if (props.containsKey("agent" + i + "HeaderName")) {
          agent.setApiKey(true);
          agent.setApiKeyHeader(props.getProperty("agent" + i + "HeaderName"));
          agent.setApiKeyValue(props.getProperty("agent" + i + "HeaderValue"));
        }
        model.getAgentList().add(agent);
      }
    }
  }

  /**
   * Scan a properties object for settings common across all scenarios.
   *
   * @param model the runtime model
   * @param props the properties object to scan.
   */
  public void readGeneralSettings(ApplicationModel model, Properties props) {
    // limit the number of steps
    if (props.containsKey("steps")) {
      model.getLimitRuntime().set(true);
      model.getMaxStepCount().set(Long.parseLong(props.getProperty("steps")));
    }
    // change the output format
    if (props.containsKey("output")) {
      switch (props.getProperty("output")) {
        case "none":
          model.setOutputFormat(OutputFormat.NONE);
          break;
        case "mp4":
          model.setOutputFormat(OutputFormat.MP4);
          break;
        case "png":
          model.setOutputFormat(OutputFormat.PNG);
          break;
        default:
          log.warn("Unknown output format");
      }
    }
    // set the output directory
    if (props.containsKey("outputDir")) {
      model.setOutputDirectory(new File(props.getProperty("outputDir")));
    }
    // write stats
    if (props.containsKey("stats")) {
      try {
        model.getStatsOptionIndex().set(Integer.parseInt(props.getProperty("stats")));
      } catch (Exception e) {
        log.warn("Error parsing stat step count");
      }
    }
  }
}
