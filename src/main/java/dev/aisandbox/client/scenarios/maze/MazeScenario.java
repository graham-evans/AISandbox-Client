package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioOption;
import dev.aisandbox.client.scenarios.maze.algorithms.Sidewinder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@Component
public class MazeScenario implements Scenario {

    private static final Logger LOG = Logger.getLogger(MazeScenario.class.getName());

    Long scenarioSalt = 0l;
    MazeRunner runner = null;

    @Override
    public String getGroup() {
        return "Introduction";
    }

    @Override
    public String getName(Locale l) {
        return "Maze Runner";
    }

    @Override
    public String getOverview(Locale l) {
        return "Navigate the maze and find the exit, then optimise the path to find the shortest route.";
    }

    @Override
    public String getDescription(Locale l) {
        return "Long description about mazes";
    }

    @Override
    public int getMinAgentCount() {
        return 1;
    }

    @Override
    public int getMaxAgentCount() {
        return 1;
    }

    @Override
    public List<ScenarioOption> getScenatioOptions(Locale l) {
        List<ScenarioOption> options = new ArrayList<>();
        options.add(new ScenarioOption(scenarioSalt, "Starting Salt (0=random)"));
        return options;
    }

    @Override
    public void startSimulation(List<Agent> agentList, GameRunController ui, FrameOutput output) {
        LOG.info("Generating maze");
        Maze maze = new Maze(40, 30);
        Sidewinder side = new Sidewinder();
        side.apply(maze);
        MazeUtilities.findFurthestPoints(maze);
        runner = new MazeRunner(agentList.get(0), maze, output, ui);
        LOG.info(maze.toString());
        LOG.info("Starting simulation");
        runner.start();
    }

    @Override
    public void stopSimulation() {
        LOG.info("Stopping simulation");
        if (runner != null) {
            runner.stopSimulation();
            runner = null;
        }
    }

    @Override
    public boolean isSimulationRunning() {
        return runner != null;
    }
}
