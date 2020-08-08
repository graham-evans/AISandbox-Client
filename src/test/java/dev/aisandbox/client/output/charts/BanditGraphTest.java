package dev.aisandbox.client.output.charts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dev.aisandbox.client.scenarios.bandit.model.Bandit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import org.junit.Test;

public class BanditGraphTest {
  @Test
  public void testBandits() throws IOException {
    BanditGraph graph = new BanditGraph(500, 400);
    // set some values
    Random nonUsedRandom = new Random();
    List<Bandit> bandits = new ArrayList<>();
    bandits.add(new Bandit(nonUsedRandom, -0.5, 1));
    bandits.add(new Bandit(nonUsedRandom, 0.0, 1.5));
    bandits.add(new Bandit(nonUsedRandom, 0.5, 1));
    graph.setBandits(bandits);
    // get the image
    BufferedImage image = graph.getImage();
    // write to file
    File outFile = new File("target/test-images/graph/bandit.png");
    outFile.getParentFile().mkdirs();
    ImageIO.write(image, "png", outFile);
    assertTrue(outFile.isFile());
  }

  @Test
  public void graphSizeConstructorTest() throws IOException {
    // check size from constructor
    BanditGraph graph = new BanditGraph(500, 400);
    // set some values
    Random nonUsedRandom = new Random();
    List<Bandit> bandits = new ArrayList<>();
    bandits.add(new Bandit(nonUsedRandom, -0.5, 1));
    bandits.add(new Bandit(nonUsedRandom, 0.0, 1.5));
    bandits.add(new Bandit(nonUsedRandom, 0.5, 1));
    graph.setBandits(bandits);
    // get the image
    BufferedImage image = graph.getImage();
    assertEquals("Image width", 500, image.getWidth());
    assertEquals("Image height", 400, image.getHeight());
  }

  @Test
  public void graphSizeMethodTest() throws IOException {
    // check size changing after constructor
    BanditGraph graph = new BanditGraph(100, 100);
    graph.setGraphHeight(400);
    graph.setGraphWidth(500);
    // set some values
    Random nonUsedRandom = new Random();
    List<Bandit> bandits = new ArrayList<>();
    bandits.add(new Bandit(nonUsedRandom, -0.5, 1));
    bandits.add(new Bandit(nonUsedRandom, 0.0, 1.5));
    bandits.add(new Bandit(nonUsedRandom, 0.5, 1));
    graph.setBandits(bandits);
    // get the image
    BufferedImage image = graph.getImage();
    assertEquals("Image width", 500, image.getWidth());
    assertEquals("Image height", 400, image.getHeight());
  }
}
