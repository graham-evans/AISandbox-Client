package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;

/**
 * Cube7x7x7 class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
public class Cube7x7x7 extends CubePuzzle implements TwistyPuzzle {

  /** Constructor for Cube7x7x7. */
  public Cube7x7x7() {
    super(6, "/dev/aisandbox/client/scenarios/twisty/cube7.png");
    log.info("Initialised 7x7x7 puzzle");
  }

  /**
   * Get the puzzle name "Cube 7x7x7 (OBTM)".
   *
   * @return the puzzle name.
   */
  @Override
  public String getPuzzleName() {
    return "Cube 7x7x7 (OBTM)";
  }
}
