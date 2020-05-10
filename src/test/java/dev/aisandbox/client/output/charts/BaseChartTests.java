package dev.aisandbox.client.output.charts;

import static org.junit.Assert.assertEquals;

import dev.aisandbox.client.output.charts.BaseChart.NiceMode;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import org.junit.Test;

public class BaseChartTests {

  public BaseChartTests() {
    File outDir = new File("target/test-images/graph/base");
    outDir.mkdirs();
  }

  @Test
  public void niceNumRoundTests() {
    assertEquals("1.245", 1.0, BaseChart.niceNum(1.245, NiceMode.ROUND), 0.00000001);
    assertEquals("12.45", 10.0, BaseChart.niceNum(12.45, NiceMode.ROUND), 0.00000001);
    assertEquals("172.43", 200.0, BaseChart.niceNum(172.43, NiceMode.ROUND), 0.00000001);
  }

  @Test
  public void chartTickTest() {
    List<Double> result = BaseChart.looseLabel(0.44, 0.52);
    System.out.println(result);
    double[] arr = result.stream().mapToDouble(d -> d).toArray();
    assertEquals("Length", 5, arr.length);
  }

  @Test
  public void chartTickTest2() {
    List<Double> result = BaseChart.looseLabel(0.0, 125.0);
    System.out.println(result);
    double[] arr = result.stream().mapToDouble(d -> d).toArray();
    assertEquals("Length", 4, arr.length);
  }

  @Test
  public void niceNumCeilTests() {
    assertEquals("1.245", 2.0, BaseChart.niceNum(1.245, NiceMode.CEIL), 0.00000001);
    assertEquals("12.45", 20.0, BaseChart.niceNum(12.45, NiceMode.CEIL), 0.00000001);
    assertEquals("172.43", 200.0, BaseChart.niceNum(172.43, NiceMode.CEIL), 0.00000001);
  }

  @Test
  public void looseNumTests() {
    List<Double> ticks = BaseChart.looseLabel(105.0, 543.0);
    assertEquals("Length", 6, ticks.size());
    assertEquals(100.0, ticks.get(0), 0.00001);
    assertEquals(200.0, ticks.get(1), 0.00001);
    assertEquals(300.0, ticks.get(2), 0.00001);
    assertEquals(400.0, ticks.get(3), 0.00001);
    assertEquals(500.0, ticks.get(4), 0.00001);
    assertEquals(600.0, ticks.get(5), 0.00001);
  }

  @Test
  public void tightNumTests() {
    List<Double> ticks = BaseChart.tightLabel(105.0, 543.0);
    assertEquals("Length", 6, ticks.size());
    assertEquals(105.0, ticks.get(0), 0.00001);
    assertEquals(200.0, ticks.get(1), 0.00001);
    assertEquals(300.0, ticks.get(2), 0.00001);
    assertEquals(400.0, ticks.get(3), 0.00001);
    assertEquals(500.0, ticks.get(4), 0.00001);
    assertEquals(543.0, ticks.get(5), 0.00001);
  }

  @Test
  public void baseChart500x350() throws IOException {
    BaseChart renderer = new BaseChart();
    renderer.resetGraph();
    BufferedImage image = renderer.getImage();
    ImageIO.write(
        renderer.getImage(), "PNG", new File("target/test-images/graph/base/500x350.png"));
    assertEquals("Default width", 500, image.getWidth());
    assertEquals("Default height", 350, image.getHeight());
  }

  @Test
  public void baseChart600x400() throws IOException {
    BaseChart renderer = new BaseChart();
    renderer.setGraphWidth(600);
    renderer.setGraphHeight(400);
    renderer.resetGraph();
    BufferedImage image = renderer.getImage();
    ImageIO.write(
        renderer.getImage(), "PNG", new File("target/test-images/graph/base/500x350.png"));
    assertEquals("Width", 600, image.getWidth());
    assertEquals("Height", 400, image.getHeight());
  }
}
