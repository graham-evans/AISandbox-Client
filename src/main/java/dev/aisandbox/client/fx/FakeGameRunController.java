package dev.aisandbox.client.fx;

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
   * @param agentURL a {@link java.lang.String} object.
   * @param e a {@link java.lang.Exception} object.
   */
  @Override
  public void showAgentError(String agentURL, Exception e) {
    // IGNORE - Do Nothing
  }

  /**
   * Overrides GameRunController method when in headless mode.
   *
   * @param agentURL a {@link java.lang.String} object.
   * @param description a {@link java.lang.String} object.
   * @param details a {@link java.lang.String} object.
   */
  @Override
  public void showAgentError(String agentURL, String description, String details) {
    // IGNORE - Do Nothing
  }
}
