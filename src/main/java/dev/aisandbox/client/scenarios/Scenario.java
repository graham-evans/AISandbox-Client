package dev.aisandbox.client.scenarios;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;

import java.util.List;
import java.util.Locale;

public interface Scenario {

    public String getGroup();

    public String getName(Locale l);

    public String getOverview(Locale l);

    public String getDescription(Locale l);

    public int getMinAgentCount();

    public int getMaxAgentCount();

    public List<ScenarioOption> getScenatioOptions(Locale l);

    public void startSimulation(List<Agent> agentList, GameRunController ui, FrameOutput output);

    public void stopSimulation();

    public boolean isSimulationRunning();
}
