package dev.aisandbox.client.profiler;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * AIProfiler class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class AIProfiler {

  @Getter long stepCount = 0;

  private long startTime = System.currentTimeMillis();

  Map<String, Double> cumulativeStepTiming = new HashMap<>();

  /**
   * getRunTime.
   *
   * @return a long.
   */
  public long getRunTime() {
    return System.currentTimeMillis() - startTime;
  }

  /**
   * getAverateStepTime.
   *
   * @return a long.
   */
  public long getAverateStepTime() {
    // TODO - This is now wrong as it assumes no break between steps!
    if (stepCount > 0) {
      return (System.currentTimeMillis() - startTime) / stepCount;
    } else {
      return 0;
    }
  }

  /**
   * addProfileStep.
   *
   * @param step a {@link dev.aisandbox.client.profiler.ProfileStep} object.
   */
  public void addProfileStep(ProfileStep step) {
    stepCount++;
    step.getTimings()
        .forEach(
            (name, value) -> {
              Double v = cumulativeStepTiming.get(name);
              if (v == null) {
                v = 0.0;
              }
              cumulativeStepTiming.put(name, v + value);
            });
  }

  /**
   * getAverageTime.
   *
   * @return a {@link java.util.Map} object.
   */
  public Map<String, Double> getAverageTime() {
    Map<String, Double> result = new HashMap<>();
    cumulativeStepTiming.forEach((name, value) -> result.put(name, value / stepCount));
    return result;
  }

  /**
   * getChart.
   *
   * @return a {@link org.jfree.chart.JFreeChart} object.
   */
  @Deprecated
  public JFreeChart getChart() {
    // convert average times to PieDataset
    Map<String, Double> times = getAverageTime();
    DefaultPieDataset dataset = new DefaultPieDataset();
    times.forEach(dataset::setValue);
    // generate the chart
    // create a chart...
    JFreeChart chart =
        ChartFactory.createPieChart(
            "Average step times",
            dataset,
            true, // legend?
            true, // tooltips?
            false // URLs?
            );
    chart.setBackgroundPaint(new Color(244, 244, 244));
    chart.getTitle().setFont(new Font("System", Font.PLAIN, 12));
    return chart;
  }

  public BufferedImage getChartImage() {
    JFreeChart chart = getChart();
    BufferedImage image = chart.createBufferedImage(300, 200);
    return image;
  }
}
