package dev.aisandbox.client.output.charts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import org.junit.Test;

public class FrequencyMassDistributionGraphTests {

  public FrequencyMassDistributionGraphTests() {
    File outDir = new File("target/test-images/graph/PMF");
    outDir.mkdirs();
  }

  @Test
  public void countValuesTest() {
    FrequencyMassDistributionGraph fhg = new FrequencyMassDistributionGraph();
    int[] data = new int[] {1, 4, 6, 7, 3};
    // put in each value once
    for (int d : data) {
      fhg.addValue(d);
    }
    Random rand = new Random();
    for (int i = 0; i < 10000; i++) {
      fhg.addValue(data[rand.nextInt(data.length)]);
    }
    assertEquals("Total Count", 10000 + data.length, fhg.getTotal());
    assertEquals("Unique Values", data.length, fhg.getUniqueValues());
  }

  @Test
  public void drawGraphTest() throws IOException {
    FrequencyMassDistributionGraph g = new FrequencyMassDistributionGraph();
    g.addValue(20);
    g.addValue(20);
    g.addValue(20);
    g.addValue(25);
    g.addValue(25);
    g.addValue(27);
    g.resetGraph();
    BufferedImage image = g.getImage();
    File outFile = new File("target/test-images/graph/PMF/500x350.png");
    ImageIO.write(image, "PNG", outFile);
    assertEquals("Default width", 500, image.getWidth());
    assertEquals("Default height", 350, image.getHeight());
    assertTrue("Output file written", outFile.isFile());
  }
}
