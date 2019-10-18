package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.scenarios.Scenario;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>MazeScenario class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
@Data
public class MazeScenario implements Scenario {

    private static final Logger LOG = Logger.getLogger(MazeScenario.class.getName());

    MazeRunner runner = null;

    // configurable properties
    private Long scenarioSalt = 0l;
    private MazeType mazeType = MazeType.BINARYTREE;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getGroup() {
        return "Introduction";
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return "Maze Runner";
    }

    /** {@inheritDoc} */
    @Override
    public String getOverview() {
        return "Navigate the maze and find the exit, then optimise the path to find the shortest route.";
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return "Long description about mazes";
    }

    /** {@inheritDoc} */
    @Override
    public int getMinAgentCount() {
        return 1;
    }

    /** {@inheritDoc} */
    @Override
    public int getMaxAgentCount() {
        return 1;
    }

    /** {@inheritDoc} */
    @Override
    public void startSimulation(List<Agent> agentList, GameRunController ui, FrameOutput output) {
        LOG.log(Level.INFO, "Salt {0}", scenarioSalt);
        LOG.info("Generating maze");
        Maze maze = new Maze(40, 30);
        Random rand = new Random(System.currentTimeMillis());
        switch (mazeType) {
            case BINARYTREE:
                MazeUtilities.applyBinaryTree(rand, maze);
                break;
            case SIDEWINDER:
                MazeUtilities.applySidewinder(rand, maze);
                break;
        }
        MazeUtilities.findFurthestPoints(maze);
        runner = new MazeRunner(agentList.get(0), maze, output, ui);
        LOG.info("Starting simulation");
        runner.start();
    }

    /** {@inheritDoc} */
    @Override
    public void stopSimulation() {
        LOG.info("Stopping simulation");
        if (runner != null) {
            runner.stopSimulation();
            runner = null;
        }
    }

    /**
     * Tell if the simulation is currently running
     *
     * @return True if the simulation is running.
     */
    @Override
    public boolean isSimulationRunning() {
        if (runner==null) {
            return false;
        }
        return runner.isRunning();
    }
}
