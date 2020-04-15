package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Cube6x6x6 extends CubePuzzle implements TwistyPuzzle {

  public Cube6x6x6() {
    super(6, "/dev/aisandbox/client/scenarios/twisty/cube6.png");
    log.info("Initialised 6x6x6 puzzle");
  }

  @Override
  public String getPuzzleName() {
    return "Cube 6x6x6 (OBTM)";
  }
}
