package dev.aisandbox.client.fx;

import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.profiler.ProfileStep;
import java.awt.image.BufferedImage;

/**
 * FakeGameRunController - used as an alternative to GameRunController when running in headless
 * mode.
 *
 * <p>Overrides all methods that would interact with the UI.
 */
public class FakeGameRunController extends GameRunController {

  /**
   * Overrides GameRunController method when in headless mode.
   *
   * @param step the ProfileStep
   */
  @Override
  public void addProfileStep(ProfileStep step) {
    // IGNORE - Do Nothing
  }

  /**
   * Overrides GameRunController method when in headless mode.
   *
   * @param image The pre-drawn {@link java.awt.image.BufferedImage} to display.
   */
  @Override
  public void updateBoardImage(BufferedImage image) {
    // IGNORE - Do Nothing
  }

  /** Overrides GameRunController method when in headless mode. */
  @Override
  public void resetStartButton() {
    // IGNORE - Do Nothing
  }

  /**
   * Overrides GameRunController method when in headless mode.
   *
   * @param e a {@link AgentException} object.
   */
  @Override
  public void showAgentError(AgentException e) {
    // IGNORE - Do Nothing
  }
}
