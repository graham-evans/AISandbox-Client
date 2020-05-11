package dev.aisandbox.client.scenarios;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import java.util.List;

/**
 * Interface class for describing and launching a scenario.
 *
 * @author gde
 * @version $Id: $Id
 */
public interface Scenario {

  /**
   * The type of scenario (used for grouping and colour-coding).
   *
   * @return a {@link dev.aisandbox.client.scenarios.ScenarioType} for this scenario
   */
  public ScenarioType getGroup();

  /**
   * The name of the scenario.
   *
   * @return a {@link java.lang.String} name.
   */
  public String getName();

  /**
   * Return a short overview of the scenario.
   *
   * @return a {@link java.lang.String} overview.
   */
  public String getOverview();

  /**
   * Return a long description of the scenario.
   *
   * @return a {@link java.lang.String} description.
   */
  public String getDescription();

  /**
   * The resource path for a 320x240 example image.
   *
   * @return a {@link java.lang.String} reference to an image.
   */
  public String getImageReference();

  /**
   * The minimum number of agents this scenario will work with.
   *
   * @return a int.
   */
  public int getMinAgentCount();

  /**
   * The maximum number of agents this scenario will work with.
   *
   * @return a int.
   */
  public int getMaxAgentCount();

  /**
   * startSimulation.
   *
   * @param agentList a {@link java.util.List} object.
   * @param ui a {@link dev.aisandbox.client.fx.GameRunController} object.
   * @param output a {@link dev.aisandbox.client.output.FrameOutput} object.
   * @param stepCount a {@link java.lang.Long} object.
   */
  public void startSimulation(
      List<Agent> agentList, GameRunController ui, FrameOutput output, Long stepCount);

  /** stopSimulation. */
  public void stopSimulation();

  /**
   * isSimulationRunning.
   *
   * @return a boolean.
   */
  public boolean isSimulationRunning();

  /** joinSimulation. */
  public void joinSimulation();

  /**
   * getScenarioURL.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getScenarioURL();

  /**
   * getSwaggerURL.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getSwaggerURL();

  /**
   * isBeta.
   *
   * @return a boolean.
   */
  public boolean isBeta();
}
