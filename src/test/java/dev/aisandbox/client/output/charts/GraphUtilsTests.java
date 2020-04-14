package dev.aisandbox.client.output.charts;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Test;

public class GraphUtilsTests {

  public GraphUtilsTests() {
    File outDir = new File("target/test-images/graph/titles");
    outDir.mkdirs();
  }

  @Test
  public void titleTest() throws IOException {
    BufferedImage title = GraphUtils.getTitle("Texty", new Font("Helvetica", Font.PLAIN, 12));
    ImageIO.write(title, "PNG", new File("target/test-images/graph/titles/Texty.png"));
  }

  @Test
  public void titleNoDecentTest() throws IOException {
    BufferedImage title = GraphUtils.getTitle("Text", new Font("Helvetica", Font.PLAIN, 12));
    ImageIO.write(title, "PNG", new File("target/test-images/graph/titles/Text.png"));
  }

  @Test
  public void titleLargeTest() throws IOException {
    BufferedImage title = GraphUtils.getTitle("Title Text", new Font("Helvetica", Font.BOLD, 32));
    ImageIO.write(title, "PNG", new File("target/test-images/graph/titles/Large.png"));
  }
}
