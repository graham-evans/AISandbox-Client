package dev.aisandbox.client.output.charts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RollingAverageGraphTests {
    @Test
    public void memorySizeTest() {
        RollingAverageGraph graph = new RollingAverageGraph(5,5);
        for (int i=0;i<20;i++) {
            graph.addValue(1.0);
        }
        assertEquals("wrong number of entries in values",5,graph.getValues().size());
        assertEquals("wrong number of entries in memory",5,graph.getMemory().size());
    }

    @Test
    public void valueTest() {
        RollingAverageGraph graph = new RollingAverageGraph(5,5);
        for (int i=1;i<10;i++) {
            graph.addValue((double) i);
        }
        assertEquals("value 5",3.0,graph.getMemory().get(5l),0.001);
        assertEquals("value 6",4.0,graph.getMemory().get(6l),0.001);
        assertEquals("value 7",5.0,graph.getMemory().get(7l),0.001);
        assertEquals("value 8",6.0,graph.getMemory().get(8l),0.001);
        assertEquals("value 9",7.0,graph.getMemory().get(9l),0.001);
    }
}
