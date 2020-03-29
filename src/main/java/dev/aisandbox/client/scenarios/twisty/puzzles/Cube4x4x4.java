package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Cube4x4x4 extends CubePuzzle implements TwistyPuzzle {

  public Cube4x4x4() {
    super(4, "/dev/aisandbox/client/scenarios/twisty/cube4.png");
    log.info("Initialised 4x4x4 puzzle");
  }

  @Override
  public String getPuzzleName() {
    return "Cube 4x4x4 (OBTM)";
  }
}
