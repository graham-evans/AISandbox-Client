package dev.aisandbox.client.output.charts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Test;

public class LineGraphTests {
  @Test
  public void memorySizeTest() {
    LineGraph graph = new LineGraph();
    graph.setMemorySize(5);
    for (int i = 0; i < 20; i++) {
      graph.addValue(1.0);
    }
    assertEquals("wrong number of entries", 5, graph.getStorage().size());
  }

  @Test
  public void writeChartTest() throws IOException {
    LineGraph graph = new LineGraph();
    graph.setMemorySize(25);
    graph.addValue(1234.0);
    graph.addValue(803.0);
    graph.addValue(430.0);
    graph.addValue(643.0);
    graph.addValue(123.0);
    graph.addValue(120.0);
    graph.addValue(120.0);
    graph.addValue(120.0);
    graph.addValue(120.0);
    BufferedImage image = graph.getGraph(300, 250);
    assertNotNull(image);
    File testFile = File.createTempFile("test", ".png");
    ImageIO.write(image, "png", testFile);
    testFile.delete();
  }
}
