package dev.aisandbox.client.math;

public class MathsTools {

  public static double incrementalAverage(double oldAverage, double newValue, int valueCount) {
    return oldAverage + (newValue - oldAverage) / (valueCount);
  }
}
