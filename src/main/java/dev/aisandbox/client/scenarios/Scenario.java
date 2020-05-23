package dev.aisandbox.client.scenarios;

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
   * Get a list of configurable parameters.
   *
   * @return a {@link java.util.List} of {@link dev.aisandbox.client.scenarios.ScenarioParameter}.
   */
  public ScenarioParameter[] getParameterArray();

  /**
   * Get a runtime object that can be called to run the AI.
   *
   * @return a {link dev.aidandbox.client.scenarios.ScenarioRuntime} object.
   */
  public ScenarioRuntime getRuntime();
}
