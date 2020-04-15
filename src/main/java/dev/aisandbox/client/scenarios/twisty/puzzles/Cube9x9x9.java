package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Cube9x9x9 extends CubePuzzle implements TwistyPuzzle {

  public Cube9x9x9() {
    super(6, "/dev/aisandbox/client/scenarios/twisty/cube9.png");
    log.info("Initialised 9x9x9 puzzle");
  }

  @Override
  public String getPuzzleName() {
    return "Cube 9x9x9 (OBTM)";
  }
}
