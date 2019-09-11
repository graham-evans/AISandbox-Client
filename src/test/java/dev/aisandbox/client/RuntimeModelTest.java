package dev.aisandbox.client;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

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

}
