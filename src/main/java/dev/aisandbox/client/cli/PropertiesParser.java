package dev.aisandbox.client.cli;

import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.output.OutputFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

  @Autowired private ApplicationContext appContext;

  /**
   * Parse a configuration file, updateing the RuntimeModel to match.
   *
   * @param model The existing runtime model
   * @param filePath a reference to the file to be parsed
   * @return the (updated) runtime model
   */
  public RuntimeModel parseConfiguration(RuntimeModel model, String filePath) {
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
  public RuntimeModel parseConfiguration(RuntimeModel model, Properties props) {
    // read scenario specific properties
    String scenario = props.getProperty("scenario");
    if (scenario != null) {
      switch (scenario) {
        case "maze":
          readMazeSettings(model, props);
          break;
        case "mine":
          readMineSettings(model, props);
          break;
        default:
          log.warn("Unknown scenario name '{}'", scenario);
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
  public void readAgentSettings(RuntimeModel model, Properties props) {
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
   * Scan a properties object for settings common accross all scenarios.
   *
   * @param model the runtime model
   * @param props the properties object to scan.
   */
  public void readGeneralSettings(RuntimeModel model, Properties props) {
    // read general properties
    if (props.containsKey("steps")) {
      model.getLimitRuntime().set(true);
      model.getMaxStepCount().set(Long.parseLong(props.getProperty("steps")));
    }
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
    if (props.containsKey("outputDir")) {
      model.setOutputDirectory(new File(props.getProperty("outputDir")));
    }
  }

  /**
   * Scan a properties object for settings specific to the Mine Hunter scenario.
   *
   * @param model the runtime model
   * @param props the properties to scan
   */
  public void readMineSettings(RuntimeModel model, Properties props) {
    //    MineHunterScenario mine = appContext.getBean(MineHunterScenario.class);
    //    model.setScenario(mine);
    //    if (props.containsKey("salt")) {
    //      mine.setScenarioSalt(Long.parseLong(props.getProperty("salt")));
    //    }
    //    if (props.containsKey("size")) {
    //      switch (props.getProperty("size")) {
    //        case "small":
    //          mine.setMineHunterBoardSize(SizeEnum.SMALL);
    //          break;
    //        case "medium":
    //          mine.setMineHunterBoardSize(SizeEnum.MEDIUM);
    //          break;
    //        case "large":
    //          mine.setMineHunterBoardSize(SizeEnum.LARGE);
    //          break;
    //        default:
    //          log.warn("unrecognised board size");
    //      }
    //    }
  }

  /**
   * Scan a properties object for settings specific to the Maze scenario.
   *
   * @param model the runtime model
   * @param props the properties to scan
   */
  public void readMazeSettings(RuntimeModel model, Properties props) {
    //    MazeScenario maze = appContext.getBean(MazeScenario.class);
    //    model.setScenario(maze);
    //    if (props.containsKey("salt")) {
    //      maze.setScenarioSalt(Long.parseLong(props.getProperty("salt")));
    //    }
    //    if (props.containsKey("size")) {
    //      switch (props.getProperty("size")) {
    //        case "small":
    //          maze.setMazeSize(MazeSize.SMALL);
    //          break;
    //        case "medium":
    //          maze.setMazeSize(MazeSize.MEDIUM);
    //          break;
    //        case "large":
    //          maze.setMazeSize(MazeSize.LARGE);
    //          break;
    //        default:
    //          log.warn("unrecognised board size");
    //      }
    //    }
    //    if (props.containsKey("type")) {
    //      switch (props.getProperty("type")) {
    //        case "binarytree":
    //          maze.setMazeType(MazeType.BINARYTREE);
    //          break;
    //        case "sidewinder":
    //          maze.setMazeType(MazeType.SIDEWINDER);
    //          break;
    //        case "recursivebacktracker":
    //          maze.setMazeType(MazeType.RECURSIVEBACKTRACKER);
    //          break;
    //        case "braided":
    //          maze.setMazeType(MazeType.BRAIDED);
    //          break;
    //        default:
    //          log.warn("unrecognised maze type");
    //      }
    //    }
  }
}
