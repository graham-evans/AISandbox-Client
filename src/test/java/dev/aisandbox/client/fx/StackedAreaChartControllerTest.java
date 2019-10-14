package dev.aisandbox.client.fx;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class StackedAreaChartControllerTest extends ApplicationTest {

    @BeforeClass
    public static void setupHeadlessMode() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    public void SingleTest() {
        StackedAreaChart areaChart = new StackedAreaChart(new NumberAxis(), new NumberAxis());
        StackedAreaChartController controller = new StackedAreaChartController(areaChart);
        Map<String, Double> reading = new TreeMap<>();
        reading.put("Initialise", 1.0);
        reading.put("Processing", 2.0);
        controller.add(reading);
        assertEquals("Should have two series", 2, areaChart.getData().size());
    }

    @Test
    public void resetTest() {
        StackedAreaChart areaChart = new StackedAreaChart(new NumberAxis(), new NumberAxis());
        StackedAreaChartController controller = new StackedAreaChartController(areaChart);
        Map<String, Double> reading = new TreeMap<>();
        reading.put("Initialise", 1.0);
        reading.put("Processing", 2.0);
        controller.add(reading);
        controller.add(reading);
        controller.add(reading);
        controller.add(reading);
        controller.reset();
        assertEquals("Shoudl have no series", 0, areaChart.getData().size());
    }

}
