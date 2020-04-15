package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Cube6x6x6 class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
@Component
public class Cube6x6x6 extends CubePuzzle implements TwistyPuzzle {

  /** Constructor for Cube6x6x6. */
  public Cube6x6x6() {
    super(6, "/dev/aisandbox/client/scenarios/twisty/cube6.png");
    log.info("Initialised 6x6x6 puzzle");
  }

  /** {@inheritDoc} */
  @Override
  public String getPuzzleName() {
    return "Cube 6x6x6 (OBTM)";
  }
}
