package dev.aisandbox.client.cli;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.OutputFormat;
import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.scenarios.maze.MazeScenario;
import dev.aisandbox.client.scenarios.maze.MazeSize;
import dev.aisandbox.client.scenarios.maze.MazeType;
import dev.aisandbox.client.scenarios.mine.MineHunterScenario;
import dev.aisandbox.client.scenarios.mine.SizeEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PropertiesParser {

    private static final Logger LOG = LoggerFactory.getLogger(PropertiesParser.class.getName());

    @Autowired
    private ApplicationContext appContext;

    public RuntimeModel parseConfiguration(RuntimeModel model, String filePath) {
        // read properties from file
        File pfile = new File(filePath);
        LOG.info("Loading properties from " + pfile.getAbsolutePath());
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(pfile));
        } catch (IOException e) {
            LOG.warn( "Error loading properties", e);
        }
        // parse and return
        return parseConfiguration(model, props);
    }

    public RuntimeModel parseConfiguration(RuntimeModel model, Properties props) {
        // read scenario specific properties
        String scenario = props.getProperty("scenario");
        if (scenario!=null) {
            switch (scenario) {
                case "maze":
                    readMazeSettings(model, props);
                    break;
                case "mine":
                    readMineSettings(model, props);
                    break;
                default:
                    LOG.warn("Unknown scenario name '{}'",scenario);
            }
        }
        readGeneralSettings(model,props);
        readAgentSettings(model, props);
        return model;
    }

    public void readAgentSettings(RuntimeModel model,Properties props) {
        if (props.containsKey("agents")) {
            int agentCount = Integer.parseInt(props.getProperty("agents"));
            for (int i=1;i<=agentCount;i++) {
                Agent agent = new Agent();
                if (props.containsKey("agent"+i+"URL")) {
                    agent.setTarget(props.getProperty("agent"+i+"URL"));
                }
                if ("XML".equals(props.getProperty("agent"+i+"Lang"))) { // we only test for XML as JSON is the default
                    agent.setEnableXML(true);
                }
                if (props.containsKey("agent"+i+"Username")) {
                    agent.setBasicAuth(true);
                    agent.setBasicAuthUsername(props.getProperty("agent"+i+"Username"));
                    agent.setBasicAuthPassword(props.getProperty("agent"+i+"Password"));
                }
                if (props.containsKey("agent"+i+"HeaderName")) {
                    agent.setApiKey(true);
                    agent.setApiKeyHeader(props.getProperty("agent"+i+"HeaderName"));
                    agent.setApiKeyValue(props.getProperty("agent"+i+"HeaderValue"));
                }
                model.getAgentList().add(agent);
            }
        }
    }

    public void readGeneralSettings(RuntimeModel model,Properties props) {
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
                    LOG.warn( "Unknown output format");
            }
        }
        if (props.containsKey("outputDir")) {
            model.setOutputDirectory(new File(props.getProperty("outputDir")));
        }
    }

    public void readMineSettings(RuntimeModel model, Properties props) {
        MineHunterScenario mine = appContext.getBean(MineHunterScenario.class);
        model.setScenario(mine);
        if (props.containsKey("salt")) {
            mine.setScenarioSalt(Long.parseLong(props.getProperty("salt")));
        }
        if (props.containsKey("size")) {
            switch (props.getProperty("size")) {
                case "small":
                    mine.setMineHunterBoardSize(SizeEnum.SMALL);
                    break;
                case "medium":
                    mine.setMineHunterBoardSize(SizeEnum.MEDIUM);
                    break;
                case "large":
                    mine.setMineHunterBoardSize(SizeEnum.LARGE);
                    break;
                default:
                    LOG.warn("unrecognised board size");
            }
        }
    }

    public void readMazeSettings(RuntimeModel model, Properties props) {
        MazeScenario maze = appContext.getBean(MazeScenario.class);
        model.setScenario(maze);
        if (props.containsKey("salt")) {
            maze.setScenarioSalt(Long.parseLong(props.getProperty("salt")));
        }
        if (props.containsKey("size")) {
            switch (props.getProperty("size")) {
                case "small":
                    maze.setMazeSize(MazeSize.SMALL);
                    break;
                case "medium":
                    maze.setMazeSize(MazeSize.MEDIUM);
                    break;
                case "large":
                    maze.setMazeSize(MazeSize.LARGE);
                    break;
                default:
                    LOG.warn("unrecognised board size");
            }
        }
        if (props.containsKey("type")) {
            switch (props.getProperty("type")) {
                case "binarytree":
                    maze.setMazeType(MazeType.BINARYTREE);
                    break;
                case "sidewinder":
                    maze.setMazeType(MazeType.SIDEWINDER);
                    break;
                case "recursivebacktracker":
                    maze.setMazeType(MazeType.RECURSIVEBACKTRACKER);
                    break;
                case "braided":
                    maze.setMazeType(MazeType.BRAIDED);
                    break;
                default:
                    LOG.warn("unrecognised maze type");
            }
        }
    }

}
