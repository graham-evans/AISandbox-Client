package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Cube9x9x9 class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
@Component
public class Cube9x9x9 extends CubePuzzle implements TwistyPuzzle {

  /** Constructor for Cube9x9x9. */
  public Cube9x9x9() {
    super(6, "/dev/aisandbox/client/scenarios/twisty/cube9.png");
    log.info("Initialised 9x9x9 puzzle");
  }

  /**
   * Get the puzzle name "Cube 9x9x9 (OBTM)".
   * @return the puzzle name.
   */
  @Override
  public String getPuzzleName() {
    return "Cube 9x9x9 (OBTM)";
  }
}
