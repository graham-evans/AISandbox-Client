package dev.aisandbox.client.profiler;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class AIProfiler {

  @Getter long stepCount = 0;

  private long startTime = System.currentTimeMillis();

  Map<String, Double> cumulativeStepTiming = new HashMap<>();

  public long getRunTime() {
    return System.currentTimeMillis() - startTime;
  }

  public long getAverateStepTime() {
    if (stepCount > 0) {
      return (System.currentTimeMillis() - startTime) / stepCount;
    } else {
      return 0;
    }
  }

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

  public Map<String, Double> getAverageTime() {
    Map<String, Double> result = new HashMap<>();
    cumulativeStepTiming.forEach((name, value) -> result.put(name, value / stepCount));
    return result;
  }

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
}
