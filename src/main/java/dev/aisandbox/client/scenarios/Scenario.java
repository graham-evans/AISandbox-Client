package dev.aisandbox.client.scenarios;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;

import java.util.List;

public interface Scenario {

    public String getGroup();

    public String getName();

    public String getOverview();

    public String getDescription();

    public int getMinAgentCount();

    public int getMaxAgentCount();

    public void startSimulation(List<Agent> agentList, GameRunController ui, FrameOutput output);

    public void stopSimulation();

    public boolean isSimulationRunning();
}
