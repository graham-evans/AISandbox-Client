package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Cube5x5x5 class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
@Component
public class Cube5x5x5 extends CubePuzzle implements TwistyPuzzle {

  /** Constructor for Cube5x5x5. */
  public Cube5x5x5() {
    super(5, "/dev/aisandbox/client/scenarios/twisty/cube5.png");
    log.info("Initialised 5x5x5 puzzle");
  }

  /** {@inheritDoc} */
  @Override
  public String getPuzzleName() {
    return "Cube 5x5x5 (OBTM)";
  }
}
