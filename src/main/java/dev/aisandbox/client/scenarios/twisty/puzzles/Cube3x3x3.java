package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Cube3x3x3 extends CubePuzzle implements TwistyPuzzle {

  public Cube3x3x3() {
    super(3, "/dev/aisandbox/client/scenarios/twisty/cube3.png");
    log.info("Initialised 3x3x3 puzzle");
  }

  @Override
  public String getPuzzleName() {
    return "Cube 3x3x3 (OBTM)";
  }
}
