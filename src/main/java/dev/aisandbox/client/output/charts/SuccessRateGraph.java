package dev.aisandbox.client.output.charts;

import java.awt.image.BufferedImage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * SuccessRateGraph class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class SuccessRateGraph extends RollingAverageGraph {

  /** Constructor for SuccessRateGraph. */
  public SuccessRateGraph() {
    super(50, 20);
  }

  /**
   * Get the graph at the required resolution.
   *
   * @param width the width of the required image.
   * @param height the height of the required image.
   * @return the BufferedImage.
   */
  @Override
  public BufferedImage getGraph(int width, int height) {

    XYSeries series1 = new XYSeries("Average");
    getMemory().forEach(series1::add);

    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);

    // create the chart
    JFreeChart chart =
        ChartFactory.createXYLineChart(
            "Success Rate",
            "Board", // x axis label
            "% Success", // y axis label
            dataset, // data
            PlotOrientation.VERTICAL,
            false, // include legend
            true, // tooltips
            false // urls
            );
    chart.getXYPlot().getRangeAxis().setRange(0.0, 100.0);

    return chart.createBufferedImage(width, height, null);
  }
}
