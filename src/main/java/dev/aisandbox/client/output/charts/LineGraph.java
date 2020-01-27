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

/** Utility class for storing and drawing trend graphs. */
public class LineGraph {

  @Getter private final TreeMap<Integer, Double> storage = new TreeMap<>();

  @Getter
  @Setter
  private int memorySize = 50;

  @Setter
  private String title = "Title";

  @Setter
  private String axisXTitle = null;

  @Setter
  private String axisYTitle = null;

  private int valueX = 0;

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
   * @param width  the width of the image
   * @param height the height of the image
   * @return the BufferedImage
   */
  public BufferedImage getGraph(int width, int height) {
    XYSeries series1 = new XYSeries("Results");
    storage.forEach(series1::add);
    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);
    // create the chart
    JFreeChart chart =
        ChartFactory.createXYLineChart(
            title,
            axisXTitle, // x axis label
            axisYTitle, // y axis label
            dataset, // data
            PlotOrientation.VERTICAL,
            false, // include legend
            true, // tooltips
            false // urls
        );
    return chart.createBufferedImage(width, height, null);
  }
}
