package dev.aisandbox.client.output.charts;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * RollingAverageGraph class.
 *
 * @author gde
 * @version $Id: $Id
 */
@RequiredArgsConstructor
public class RollingAverageGraph {

  @Getter(AccessLevel.PACKAGE)
  List<Double> values = new ArrayList<>();

  @Getter(AccessLevel.PACKAGE)
  private final TreeMap<Long, Double> memory = new TreeMap<>();

  long count;

  private final int graphSize;
  private final int window;

  @Setter private String title = "Title";

  @Setter private String xaxistitle = null;

  @Setter private String yaxistitle = null;

  /**
   * addValue.
   *
   * @param value a double.
   */
  public void addValue(double value) {
    // advance value count
    count++;
    // add the record to the list of values
    values.add(value);
    // if we have too many values, drop the oldest
    if (values.size() > window) {
      values.remove(0);
    }
    // calc the average of the current values
    memory.put(count, values.stream().mapToDouble(a -> a).average().getAsDouble());
    // do we have too many data points
    memory.remove(count - graphSize);
  }

  /**
   * getGraph.
   *
   * @param width a int.
   * @param height a int.
   * @return a {@link java.awt.image.BufferedImage} object.
   */
  public BufferedImage getGraph(int width, int height) {
    XYSeries series1 = new XYSeries("Average");
    memory.forEach(series1::add);
    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);
    // create the chart
    JFreeChart chart =
        ChartFactory.createXYLineChart(
            title,
            xaxistitle, // x axis label
            yaxistitle, // y axis label
            dataset, // data
            PlotOrientation.VERTICAL,
            false, // include legend
            true, // tooltips
            false // urls
            );
    return chart.createBufferedImage(width, height, null);
  }
}
