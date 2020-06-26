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

@Slf4j
public class AverageRewardGraph {

  private final int steps;
  @Getter private double[] aveRewards;
  private int[] trials;

  public AverageRewardGraph(int steps) {
    this.steps = steps;
    aveRewards = new double[steps];
    trials = new int[steps];
  }

  public void addReward(int step, double reward) {
    trials[step]++;
    aveRewards[step] = MathsTools.incrementalAverage(aveRewards[step], reward, trials[step]);
  }

  public BufferedImage getGraph(int width, int height) {
    XYSeries series1 = new XYSeries("Average reward");
    for (int i = 0; i < aveRewards.length; i++) {
      series1.add((i + 1), aveRewards[i]);
    }
    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);
    // create the chart
    JFreeChart chart =
        ChartFactory.createXYLineChart(
            "Average Reward",
            "Average Reward", // x axis label
            "Steps", // y axis label
            dataset, // data
            PlotOrientation.VERTICAL,
            false, // include legend
            true, // tooltips
            false // urls
            );
    return chart.createBufferedImage(width, height, null);
  }
}
