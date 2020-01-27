package dev.aisandbox.client.fx;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StackedAreaChartController {

  private final StackedAreaChart chart;
  private final NumberAxis axisX;
  private static final Logger LOG =
      LoggerFactory.getLogger(StackedAreaChartController.class.getName());
  // map of series
  private final Map<String, XYChart.Series> seriesMap = new HashMap<>();
  private int step = 0;
  private static final int HISTORY = 50;

  public StackedAreaChartController(StackedAreaChart chart) {
    this.chart = chart;
    chart.setAnimated(false);
    axisX = (NumberAxis) chart.getXAxis();
    axisX.setForceZeroInRange(false);
    axisX.setTickUnit(1.0);
    axisX.setAutoRanging(false);
  }

  public void add(Map<String, Double> timings) {
    step++;
    // remove old entries
    int oldStep = step - HISTORY;
    LOG.debug("Moving chart bounds to {} - {}", oldStep, step);
    // check existing series
    for (XYChart.Series series : seriesMap.values()) {
      if (!series.getData().isEmpty()) {
        XYChart.Data data = (XYChart.Data) series.getData().get(0);
        if (data.getXValue().equals(oldStep)) {
          series.getData().remove(data);
        }
      }
    }
    // cycle through the series
    timings.forEach(
        (sname, svalue) -> {
          // do we already have this series created
          XYChart.Series series;
          if (seriesMap.containsKey(sname)) {
            series = seriesMap.get(sname);
          } else {
            series = new XYChart.Series();
            series.setName(sname);
            chart.getData().add(series);
            seriesMap.put(sname, series);
          }
          series.getData().add(new XYChart.Data(step, svalue));
        });
    axisX.setLowerBound(oldStep + 1.0);
    axisX.setUpperBound(step);
  }

  public void reset() {
    chart.getData().clear();
    seriesMap.clear();
    step = 0;
    axisX.setLowerBound(0);
    axisX.setUpperBound(HISTORY);
  }
}
