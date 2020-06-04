package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;

/**
 * Cube4x4x4 class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
public class Cube4x4x4 extends CubePuzzle implements TwistyPuzzle {

  /** Constructor for Cube4x4x4. */
  public Cube4x4x4() {
    super(4, "/dev/aisandbox/client/scenarios/twisty/cube4.png");
    log.info("Initialised 4x4x4 puzzle");
  }

  /**
   * Get the puzzle name "Cube 4x4x4 (OBTM)".
   *
   * @return the puzzle name.
   */
  @Override
  public String getPuzzleName() {
    return "Cube 4x4x4 (OBTM)";
  }
}
