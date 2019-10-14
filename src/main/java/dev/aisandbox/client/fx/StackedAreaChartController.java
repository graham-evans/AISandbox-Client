package dev.aisandbox.client.fx;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class StackedAreaChartController {

    private final StackedAreaChart chart;
    private static final Logger LOG = Logger.getLogger(StackedAreaChartController.class.getName());
    // store the list of categories
    private final ObservableList<String> categories = FXCollections.observableList(new ArrayList<>());
    private final Map<String, XYChart.Series> seriesMap = new HashMap<>();
    private int step = 0;
    private static final int HISTORY = 20;

    public StackedAreaChartController(StackedAreaChart chart) {
        this.chart = chart;
        ((CategoryAxis) chart.getXAxis()).setCategories(categories);
        chart.setAnimated(false);
    }

    public void add(Map<String, Double> timings) {
        step++;
        String sstep = Integer.toString(step);
        // remove old entries
        LOG.info("Removing old data");
        String oldStep = Integer.toString(step - HISTORY);
        // check existing series
        for (XYChart.Series series : seriesMap.values()) {
            if (!series.getData().isEmpty()) {
                XYChart.Data data = (XYChart.Data) series.getData().get(0);
                if (data.getXValue().equals(oldStep)) {
                    series.getData().remove(data);
                }
            }
        }
        LOG.info("Remove old cat");
        categories.remove(oldStep);
        // add this new step as a category
        LOG.info("Add new cat");
        categories.add(sstep);
        LOG.info("Add new data");
        // cycle through the series
        for (String sname : timings.keySet()) {
            double svalue = timings.get(sname);
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
            series.getData().add(new XYChart.Data(sstep, svalue));
        }
    }

    public void reset() {
        chart.getData().clear();
        categories.clear();
        seriesMap.clear();
        step = 0;
    }

}
