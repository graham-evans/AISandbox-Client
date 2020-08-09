package dev.aisandbox.client.math;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathsTools {

  public static double incrementalAverage(double oldAverage, double newValue, int valueCount) {
    return oldAverage + (newValue - oldAverage) / (valueCount);
  }
}
