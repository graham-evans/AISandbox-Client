package dev.aisandbox.client.scenarios;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import java.awt.image.BufferedImage;
import java.util.List;

public interface ScenarioRuntime {

  public void setAgents(List<Agent> agents);

  public BufferedImage advance() throws AgentException;

  public String getStatistics();
}
