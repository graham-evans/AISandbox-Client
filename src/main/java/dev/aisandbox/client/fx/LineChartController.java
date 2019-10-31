package dev.aisandbox.client.fx;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import lombok.AllArgsConstructor;

public class LineChartController {

    private final LineChart<Number,Number> chart;

    private final NumberAxis xAxis;
    private final NumberAxis yAxis;

    XYChart.Series series = new XYChart.Series();

    long step=1;

    public LineChartController(LineChart chart) {
        this.chart = chart;
        chart.setAnimated(false);
        xAxis = (NumberAxis) chart.getXAxis();
        yAxis = (NumberAxis) chart.getYAxis();

        xAxis.setForceZeroInRange(false);
        xAxis.setTickUnit(1.0);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(25);
        chart.getData().add(series);
    }

    public void addReward(double value) {
        series.getData().add(new XYChart.Data(step,value));
        step++;
    }
}
