package dev.aisandbox.client.scenarios.zebra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PuzzleTests {

  Random rand = new Random();
  private static final Logger LOG = LoggerFactory.getLogger(PuzzleTests.class.getName());

  @Test
  public void tinyTest() {
    ZebraPuzzle puzzle = new ZebraPuzzle(rand, 1, 1);
    puzzle.generateSolution();
    puzzle.generateClues();
    // there should be a single clue
    assertEquals("Clue count", 1, puzzle.getRawClueList().size());
    LOG.info("Clues:");
    for (Clue clue : puzzle.getRawClueList()) {
      LOG.info(clue.getClueString());
    }
  }

  @Test
  public void grid2x2Test() {
    ZebraPuzzle puzzle = new ZebraPuzzle(rand, 2, 2);
    puzzle.generateSolution();
    puzzle.generateClues();
    for (Clue clue : puzzle.getClueList()) {
      LOG.info(clue.getClueString());
    }
    assertTrue(true);
  }
}
