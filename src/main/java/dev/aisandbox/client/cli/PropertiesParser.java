package dev.aisandbox.client.cli;

import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.scenarios.maze.MazeScenario;
import dev.aisandbox.client.scenarios.maze.MazeSize;
import dev.aisandbox.client.scenarios.maze.MazeType;
import dev.aisandbox.client.scenarios.mine.MineHunterScenario;
import dev.aisandbox.client.scenarios.mine.SizeEnum;

import java.util.Properties;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class PropertiesParser {

private static final Logger LOG = Logger.getLogger(PropertiesParser.class.getName());

    public RuntimeModel parseConfiguration(RuntimeModel model, String filePath) {
        // read properties from file
        Properties props = new Properties();
        // parse and return
        return parseConfiguration(model, props);
    }

    public RuntimeModel parseConfiguration(RuntimeModel model, Properties props) {
          switch (props.getProperty("scenario")) {
            case "maze" :
                readMazeSettings(model, props);
                break;
            case "mine" :
                readMineSettings(model,props);
                break;
        }
   
        return model;
    }

    public void readMineSettings(RuntimeModel model, Properties props) {
        MineHunterScenario mine = new MineHunterScenario();
        model.setScenario(mine);
        if (props.containsKey("salt")) {
            mine.setScenarioSalt(Long.parseLong(props.getProperty("salt")));
        }
         if (props.containsKey("size")) {
            switch(props.getProperty("size")) {
                case "small" :
                    mine.setMineHunterBoardSize(SizeEnum.SMALL);
                    break;
                    case "medium" :
                    mine.setMineHunterBoardSize(SizeEnum.MEDIUM);
                    break;
                    case "large" : 
                    mine.setMineHunterBoardSize(SizeEnum.LARGE);
                    break;
                default: 
                    LOG.warning("unrecognised board size");
            }
        }
    }

public void readMazeSettings(RuntimeModel model, Properties props) {
        MazeScenario maze = new MazeScenario();
        model.setScenario(maze);
        if (props.containsKey("salt")) {
            maze.setScenarioSalt(Long.parseLong(props.getProperty("salt")));
        }
        if (props.containsKey("size")) {
            switch(props.getProperty("size")) {
                case "small" :
                    maze.setMazeSize(MazeSize.SMALL);
                    break;
                    case "medium" :
                    maze.setMazeSize(MazeSize.MEDIUM);
                    break;
                    case "large" :
                    maze.setMazeSize(MazeSize.LARGE);
                    break;
                default: 
                    LOG.warning("unrecognised board size");
            }
        }
        if (props.containsKey("type")) {
            switch(props.getProperty("type")) {
                case "binarytree" :
                    maze.setMazeType(MazeType.BINARYTREE);
                    break;
                case "sidewinder" :
                    maze.setMazeType(MazeType.SIDEWINDER);
                    break;
                case "recursivebacktracker" :
                    maze.setMazeType(MazeType.RECURSIVEBACKTRACKER);
                    break;
                case "braided" : 
                    maze.setMazeType(MazeType.BRAIDED);
                    break;
                default: 
                    LOG.warning("unrecognised maze type");
            }
        }
    }

}