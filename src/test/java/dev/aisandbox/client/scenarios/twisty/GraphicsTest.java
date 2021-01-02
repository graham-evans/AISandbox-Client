package dev.aisandbox.client.scenarios.twisty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.junit.Test;

public class GraphicsTest {

  static List<TwistyPuzzle> puzzleList = new ArrayList();

  @Test
  public void createImageTest() throws IOException {
    for (PuzzleType pt : PuzzleType.values()) {
      TPPuzzle puzzle = new TPPuzzle(pt.getResource(), pt.getID());
      puzzle.resetPuzzle();
      File out = new File("target/test-images/twisty/" + puzzle.getPuzzleName() + "/puzzle.png");
      out.getParentFile().mkdirs();
      BufferedImage image = puzzle.getStateImage();
      ImageIO.write(image, "PNG", out);
    }
    assertTrue(true);
  }

  @Test
  public void createMoveImageTest() throws IOException, NotExistentMoveException {
    for (PuzzleType pt : PuzzleType.values()) {
      TPPuzzle puzzle = new TPPuzzle(pt.getResource(), pt.getID());
      for (int i = 0; i < puzzle.getMoveList().size(); i++) {
        String move = puzzle.getMoveList().get(i);
        // create image showing the result of the move from the starting positions
        puzzle.resetPuzzle();
        File moveOutputFile =
            new File(
                "target/test-images/twisty/"
                    + puzzle.getPuzzleName()
                    + "/"
                    + i
                    + "-"
                    + move
                    + ".png");
        moveOutputFile.getParentFile().mkdirs();
        puzzle.applyMove(move);
        BufferedImage image = puzzle.getStateImage();
        // test image is created
        assertNotNull("move image generated", image);
        assertNotEquals("Move image has zero width", 0, image.getWidth());
        assertNotEquals("Move image has zero height", 0, image.getHeight());
        ImageIO.write(image, "PNG", moveOutputFile);
        assertTrue("Move file written", moveOutputFile.isFile());
        // move icon image
        File iconOutputFile =
            new File(
                "target/test-images/twisty/"
                    + puzzle.getPuzzleName()
                    + "/"
                    + i
                    + "-"
                    + move
                    + "-icon.png");
        image = puzzle.getMoveImage(move);
        assertNotNull("Null Icon image", image);
        assertEquals("icon image width", 60, image.getWidth());
        assertEquals("icon image height", 100, image.getHeight());
        ImageIO.write(image, "PNG", iconOutputFile);
        assertTrue("icon image not written to file", iconOutputFile.isFile());
      }
    }
  }

  @Test
  public void createMoveSpriteSheet() throws IOException, NotExistentMoveException {
    for (PuzzleType pt : PuzzleType.values()) {
      TPPuzzle puzzle = new TPPuzzle(pt.getResource(), pt.getID());
      puzzle.resetPuzzle();
      File out = new File("target/test-images/twisty/" + puzzle.getPuzzleName() + "/sprites.png");
      out.getParentFile().mkdirs();
      BufferedImage image = puzzle.createMoveSpriteSheet();
      ImageIO.write(image, "PNG", out);
    }
    assertTrue(true);
  }
}
