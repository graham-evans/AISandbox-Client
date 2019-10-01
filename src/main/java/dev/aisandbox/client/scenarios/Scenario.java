package dev.aisandbox.client.scenarios;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;

import java.util.List;

/**
 * <p>Scenario interface.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
public interface Scenario {

    /**
     * <p>getGroup.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getGroup();

    /**
     * <p>getName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName();

    /**
     * <p>getOverview.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getOverview();

    /**
     * <p>getDescription.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDescription();

    /**
     * <p>getMinAgentCount.</p>
     *
     * @return a int.
     */
    public int getMinAgentCount();

    /**
     * <p>getMaxAgentCount.</p>
     *
     * @return a int.
     */
    public int getMaxAgentCount();

    /**
     * <p>startSimulation.</p>
     *
     * @param agentList a {@link java.util.List} object.
     * @param ui        a {@link dev.aisandbox.client.fx.GameRunController} object.
     * @param output    a {@link dev.aisandbox.client.output.FrameOutput} object.
     */
    public void startSimulation(List<Agent> agentList, GameRunController ui, FrameOutput output);

    /**
     * <p>stopSimulation.</p>
     */
    public void stopSimulation();

    /**
     * <p>isSimulationRunning.</p>
     *
     * @return a boolean.
     */
    public boolean isSimulationRunning();
}
