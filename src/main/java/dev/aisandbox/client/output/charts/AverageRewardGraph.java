package dev.aisandbox.client.output.charts;

import dev.aisandbox.client.math.MathsTools;
import java.awt.image.BufferedImage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/** Graph showing the average reward when repeating N trials. */
@Slf4j
public class AverageRewardGraph extends BaseJFreeGraph implements OutputGraph {

  @Getter private double[] aveRewards;
  private int[] trials;

  /**
   * Create an Average Reward graph with the specified width, height and number of steps
   *
   * @param width The width of the graph (in pixels).
   * @param height The height of the graph (in pixels).
   * @param steps The number of steps (results) in each run.
   */
  public AverageRewardGraph(int width, int height, int steps) {
    title = "Average Reward";
    xaxisHeader = "Steps";
    yaxisHeader = "Average Reward";
    graphHeight = height;
    graphWidth = width;
    aveRewards = new double[steps];
    trials = new int[steps];
  }

  public void addReward(int step, double reward) {
    trials[step]++;
    aveRewards[step] = MathsTools.incrementalAverage(aveRewards[step], reward, trials[step]);
  }

  @Override
  public BufferedImage getImage() {
    XYSeries series1 = new XYSeries("Average reward");
    for (int i = 0; i < aveRewards.length; i++) {
      series1.add((i + 1), aveRewards[i]);
    }
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
