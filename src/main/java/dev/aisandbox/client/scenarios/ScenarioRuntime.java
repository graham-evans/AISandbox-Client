package dev.aisandbox.client.scenarios;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import java.io.File;
import java.util.List;

public interface ScenarioRuntime {

  public void setAgents(List<Agent> agents);

  public void initialise();

  public RuntimeResponse advance() throws AgentException, SimulationException;

  public void writeStatistics(File statisticsOutputFile);
}
