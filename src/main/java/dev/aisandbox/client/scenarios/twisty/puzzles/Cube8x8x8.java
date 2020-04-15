package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Cube8x8x8 extends CubePuzzle implements TwistyPuzzle {

  public Cube8x8x8() {
    super(6, "/dev/aisandbox/client/scenarios/twisty/cube8.png");
    log.info("Initialised 8x8x8 puzzle");
  }

  @Override
  public String getPuzzleName() {
    return "Cube 8x8x8 (OBTM)";
  }
}
