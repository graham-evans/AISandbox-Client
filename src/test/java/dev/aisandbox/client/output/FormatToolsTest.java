package dev.aisandbox.client.output;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatToolsTest {
    @Test
    public void formatZeroTest() {
        assertEquals("0:00:00.000",FormatTools.formatTime(0));
    }

    @Test
    public void format100Test() {
        assertEquals("0:00:00.100",FormatTools.formatTime(100));
    }

    @Test
    public void format10SecTest() {
        assertEquals("0:00:10.000",FormatTools.formatTime(10000));
    }

    @Test
    public void format10MinTest() {
        assertEquals("0:10:00.000",FormatTools.formatTime(600000));
    }

    @Test
    public void format1HourTest() {
        assertEquals("1:00:00.000",FormatTools.formatTime(60*60*1000));
    }

    @Test
    public void format10HourTest() {
        assertEquals("10:00:00.000",FormatTools.formatTime(10*60*60*1000));
    }

    @Test
    public void format100HourTest() {
        assertEquals("100:00:00.000",FormatTools.formatTime(100*60*60*1000));
    }
}
