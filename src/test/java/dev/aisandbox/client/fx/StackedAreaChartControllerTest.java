package dev.aisandbox.client.fx;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class StackedAreaChartControllerTest extends ApplicationTest {

    @Test
    public void SingleTest() {
        StackedAreaChart areaChart = new StackedAreaChart(new CategoryAxis(), new NumberAxis());
        StackedAreaChartController controller = new StackedAreaChartController(areaChart);
        Map<String, Double> reading = new TreeMap<>();
        reading.put("Initialise", 1.0);
        reading.put("Processing", 2.0);
        controller.add(reading);
        assertEquals("Should have one categories", 1, ((CategoryAxis) areaChart.getXAxis()).getCategories().size());
        assertEquals("Should have two series", 2, areaChart.getData().size());
    }

    @Test
    public void resetTest() {
        StackedAreaChart areaChart = new StackedAreaChart(new CategoryAxis(), new NumberAxis());
        StackedAreaChartController controller = new StackedAreaChartController(areaChart);
        Map<String, Double> reading = new TreeMap<>();
        reading.put("Initialise", 1.0);
        reading.put("Processing", 2.0);
        controller.add(reading);
        controller.add(reading);
        controller.add(reading);
        controller.add(reading);
        controller.reset();
        assertEquals("Should have no categories", 0, ((CategoryAxis) areaChart.getXAxis()).getCategories().size());
        assertEquals("Shoudl have no series", 0, areaChart.getData().size());
    }

}
