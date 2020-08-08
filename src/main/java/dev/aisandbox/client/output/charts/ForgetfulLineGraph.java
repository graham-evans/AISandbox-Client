package dev.aisandbox.client.output.charts;

import java.awt.image.BufferedImage;
import java.util.TreeMap;
import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * A graph showing the last N results. Useful when older results are less valid;
 *
 * @author gde
 * @version $Id: $Id
 */
public class ForgetfulLineGraph extends BaseJFreeGraph implements OutputGraph {

  @Getter private final TreeMap<Integer, Double> storage = new TreeMap<>();

  @Getter @Setter private int memorySize = 50;

  private int valueX = 0;

  public ForgetfulLineGraph(int width, int height) {
    graphWidth = width;
    graphHeight = height;
  }

  public ForgetfulLineGraph(int width, int height, int memorySize) {
    graphWidth = width;
    graphHeight = height;
    this.memorySize = memorySize;
  }

  /**
   * Add a value to the graph.
   *
   * @param v the value to add.
   */
  public void addValue(Double v) {
    valueX++;
    storage.put(valueX, v);
    while (storage.size() > memorySize) {
      storage.remove(storage.firstKey());
    }
  }

  /**
   * Render the graph to a bufferedimage.
   *
   * @return the BufferedImage
   */
  public BufferedImage getImage() {
    XYSeries series1 = new XYSeries("Results");
    storage.forEach(series1::add);
    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);
    // create the chart
    JFreeChart chart =
        ChartFactory.createXYLineChart(
            title,
            xaxisHeader, // x axis label
            yaxisHeader, // y axis label
            dataset, // data
            PlotOrientation.VERTICAL,
            false, // include legend
            true, // tooltips
            false // urls
            );
    return chart.createBufferedImage(graphWidth, graphHeight, null);
  }
}
