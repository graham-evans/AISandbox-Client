package dev.aisandbox.client.fx;

import dev.aisandbox.client.profiler.ProfileStep;
import java.awt.image.BufferedImage;

/**
 * FakeGameRunController class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class FakeGameRunController extends GameRunController {

  /** {@inheritDoc} */
  @Override
  public void addProfileStep(ProfileStep step) {
    // IGNORE - Do Nothing
  }

  /** {@inheritDoc} */
  @Override
  public void updateBoardImage(BufferedImage image) {
    // IGNORE - Do Nothing
  }

  /** {@inheritDoc} */
  @Override
  public void resetStartButton() {
    // IGNORE - Do Nothing
  }

  /** {@inheritDoc} */
  @Override
  public void showAgentError(String agentURL, Exception e) {
    // IGNORE - Do Nothing
  }

  /** {@inheritDoc} */
  @Override
  public void showAgentError(String agentURL, String description, String details) {
    // IGNORE - Do Nothing
  }
}
