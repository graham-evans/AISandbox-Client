package dev.aisandbox.client;

import dev.aisandbox.client.scenarios.maze.MazeScenario;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class RuntimeModelTest {

    @Test
    public void testZeroAgents() {
        RuntimeModel model = new RuntimeModel();
        model.getMinAgents().set(2);
        model.getMaxAgents().set(4);
        assertFalse("Validity when zero agents", model.getValid().get());
    }

    @Test
    public void testMinAgents() {
        RuntimeModel model = new RuntimeModel();
        model.getMinAgents().set(2);
        model.getMaxAgents().set(4);
        model.getAgentList().add(new Agent());
        model.getAgentList().add(new Agent());
        assertTrue("Validity when min agents", model.getValid().get());
    }

    @Test
    public void testMaxAgents() {
        RuntimeModel model = new RuntimeModel();
        model.getMinAgents().set(2);
        model.getMaxAgents().set(4);
        model.getAgentList().add(new Agent());
        model.getAgentList().add(new Agent());
        model.getAgentList().add(new Agent());
        model.getAgentList().add(new Agent());
        assertTrue("Validity when max agents", model.getValid().get());
    }

    @Test
    public void testTooManyAgents() {
        RuntimeModel model = new RuntimeModel();
        model.getMinAgents().set(2);
        model.getMaxAgents().set(4);
        model.getAgentList().add(new Agent());
        model.getAgentList().add(new Agent());
        model.getAgentList().add(new Agent());
        model.getAgentList().add(new Agent());
        model.getAgentList().add(new Agent());
        assertFalse("Validity when too many agents", model.getValid().get());
    }

    @Test
    public void testLoadScenario() {
        RuntimeModel model = new RuntimeModel();
        MazeScenario maze = new MazeScenario();
        model.setScenario(maze);
        assertEquals("Wrong Min Agents", 1, model.getMinAgents().get());
        assertEquals("Wrong Max Agents", 1, model.getMaxAgents().get());
        assertSame("Unexpected scenario", maze, model.getScenario());
    }

}
