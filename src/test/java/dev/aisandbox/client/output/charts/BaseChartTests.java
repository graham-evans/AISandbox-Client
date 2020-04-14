package dev.aisandbox.client.output.charts;

import static org.junit.Assert.assertEquals;

import dev.aisandbox.client.output.charts.BaseChart.NiceMode;
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
  public void baseChart500x350() throws IOException {
    BaseChart renderer = new BaseChart();
    renderer.resetGraph();
    ImageIO.write(
        renderer.getImage(), "PNG", new File("target/test-images/graph/base/500x350.png"));
  }
}
