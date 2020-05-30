package dev.aisandbox.client;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.twisty.TwistyScenario;
import org.junit.Test;

public class ApplicationModelTest {

  @Test
  public void testZeroAgents() {
    ApplicationModel model = new ApplicationModel();
    model.getMinAgents().set(2);
    model.getMaxAgents().set(4);
    assertFalse("Validity when zero agents", model.getValid().get());
  }

  @Test
  public void testMinAgents() {
    ApplicationModel model = new ApplicationModel();
    model.getMinAgents().set(2);
    model.getMaxAgents().set(4);
    model.getAgentList().add(new Agent());
    model.getAgentList().add(new Agent());
    assertTrue("Validity when min agents", model.getValid().get());
  }

  @Test
  public void testMaxAgents() {
    ApplicationModel model = new ApplicationModel();
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
    ApplicationModel model = new ApplicationModel();
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
    ApplicationModel model = new ApplicationModel();
    Scenario s = new TwistyScenario();
    model.setScenario(s);
    assertEquals("Wrong Min Agents", 1, model.getMinAgents().get());
    assertEquals("Wrong Max Agents", 1, model.getMaxAgents().get());
    assertSame("Unexpected scenario", s, model.getScenario());
  }
}
