package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Cube10x10x10 extends CubePuzzle implements TwistyPuzzle {
  public Cube10x10x10() {
    super(10, "/dev/aisandbox/client/scenarios/twisty/cube10.png");
    log.info("Initialised 10x10x10 puzzle");
  }

  @Override
  public String getPuzzleName() {
    return "Cube 10x10x10 (OBTM)";
  }
}
