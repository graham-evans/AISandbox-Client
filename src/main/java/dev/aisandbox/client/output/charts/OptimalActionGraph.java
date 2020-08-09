package dev.aisandbox.client.output.charts;

import dev.aisandbox.client.math.MathsTools;
import java.awt.image.BufferedImage;
import lombok.Getter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class OptimalActionGraph {

  @Getter private double[] aveRewards;
  private int[] trials;

  public OptimalActionGraph(int steps) {
    aveRewards = new double[steps];
    trials = new int[steps];
  }

  public void addReward(int step, double reward) {
    trials[step]++;
    aveRewards[step] = MathsTools.incrementalAverage(aveRewards[step], reward, trials[step]);
  }

  public BufferedImage getGraph(int width, int height) {
    XYSeries series1 = new XYSeries("series1");
    for (int i = 0; i < aveRewards.length; i++) {
      series1.add((i + 1), aveRewards[i]);
    }
    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);
    // create the chart
    JFreeChart chart =
        ChartFactory.createXYLineChart(
            "Optimal Action",
            "Steps", // x axis label
            "% Optimal", // y axis label
            dataset, // data
            PlotOrientation.VERTICAL,
            false, // include legend
            true, // tooltips
            false // urls
            );
    return chart.createBufferedImage(width, height, null);
  }
}
