package dev.aisandbox.client.scenarios.twisty.puzzles;

import static org.junit.Assert.assertTrue;

import dev.aisandbox.client.scenarios.twisty.NotExistentMoveException;
import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import dev.aisandbox.launcher.AISandboxCLI;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AISandboxCLI.class})
@SpringBootTest
public class GraphicsTest {
  @Autowired List<TwistyPuzzle> puzzleList;

  @Test
  public void createImageTest() throws IOException {
    for (TwistyPuzzle puzzle : puzzleList) {
      puzzle.resetPuzzle();
      File out = new File("target/test-images/twisty/" + puzzle.getPuzzleName() + ".png");
      out.getParentFile().mkdirs();
      BufferedImage image = puzzle.getStateImage();
      ImageIO.write(image, "PNG", out);
    }
    assertTrue(true);
  }

  @Test
  public void createMoveImageTest() throws IOException, NotExistentMoveException {
    for (TwistyPuzzle puzzle : puzzleList) {
      for (String move : puzzle.getMoveList()) {
        puzzle.resetPuzzle();
        File out =
            new File("target/test-images/twisty/" + puzzle.getPuzzleName() + "-" + move + ".png");
        out.getParentFile().mkdirs();
        puzzle.applyMove(move);
        BufferedImage image = puzzle.getStateImage();
        ImageIO.write(image, "PNG", out);
      }
    }
    assertTrue(true);
  }
}
