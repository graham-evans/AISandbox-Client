package dev.aisandbox.client.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathToolsAverageTest {

  @Test
  public void testFirstValue() {
    assertEquals("First value", 3.4, MathsTools.incrementalAverage(0.0, 3.4, 1), 0.00001);
  }

  @Test
  public void testSecondValue() {
    double v1 = MathsTools.incrementalAverage(0.0, 3.4, 1);
    assertEquals("Second value", 4.05, MathsTools.incrementalAverage(v1, 4.7, 2), 0.00001);
  }

  @Test
  public void fullTest() {
    double[] values = new double[] {0.0, 1.564, 2.46, -2.7, 12.43, 100.0, -0.42};
    for (int i = 0; i < values.length; i++) {
      // calculate the average of the numbers between values[0] and values[i]
      double total = 0.0;
      for (int j = 0; j <= i; j++) {
        total += values[j];
      }
      double calcAverage = total / (i + 1);
      // use the incremental average calculator to do the same sum
      double incAverage = 0.0;
      for (int j = 0; j <= i; j++) {
        incAverage = MathsTools.incrementalAverage(incAverage, values[j], j + 1);
      }
      assertEquals(
          "Incramental average of first " + (i + 1) + " values of " + values.toString(),
          calcAverage,
          incAverage,
          0.00001);
    }
  }
}
