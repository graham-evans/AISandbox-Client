package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * A 2x2x2 Cube.
 */
@Slf4j
@Component
public class Cube2x2x2 extends CubePuzzle implements TwistyPuzzle {

  public Cube2x2x2() {
    super(2, "/dev/aisandbox/client/scenarios/twisty/cube2.png");
    log.info("Initialised 2x2x2 puzzle");
  }

  /**
   * Return the puzzle name "Cube 2x2x2 (OBTM)".
   * @return the name of the puzzle
   */
  @Override
  public String getPuzzleName() {
    return "Cube 2x2x2 (OBTM)";
  }
}
