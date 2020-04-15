package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Cube3x3x3 class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
@Component
public class Cube3x3x3 extends CubePuzzle implements TwistyPuzzle {

  /** Constructor for Cube3x3x3. */
  public Cube3x3x3() {
    super(3, "/dev/aisandbox/client/scenarios/twisty/cube3.png");
    log.info("Initialised 3x3x3 puzzle");
  }

  /** {@inheritDoc} */
  @Override
  public String getPuzzleName() {
    return "Cube 3x3x3 (OBTM)";
  }
}
