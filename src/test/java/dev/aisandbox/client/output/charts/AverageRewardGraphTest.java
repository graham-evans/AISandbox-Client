package dev.aisandbox.client.output.charts;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import org.junit.Test;

public class AverageRewardGraphTest {
  @Test
  public void averageRewardGraphTest() throws IOException {
    AverageRewardGraph graph = new AverageRewardGraph(500, 400, 20);
    Random r = new Random();
    for (int loop = 0; loop < 100; loop++) {
      for (int test = 0; test < 20; test++) {
        graph.addReward(test, r.nextGaussian());
      }
    }
    // this should draw a line centered around y=0;
    // get the image
    BufferedImage image = graph.getImage();
    // write to file
    File outFile = new File("target/test-images/graph/averageReward.png");
    outFile.getParentFile().mkdirs();
    ImageIO.write(image, "png", outFile);
    assertTrue(outFile.isFile());
  }
}
