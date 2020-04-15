package dev.aisandbox.client.profiler;

import java.util.Map;
import java.util.TreeMap;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * ProfileStep class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class ProfileStep {
  private long cursor;

  @Getter(AccessLevel.PROTECTED)
  private Map<String, Long> timings = new TreeMap<>();

  /** Constructor for ProfileStep. */
  public ProfileStep() {
    cursor = System.currentTimeMillis();
  }

  /**
   * addStep.
   *
   * @param name a {@link java.lang.String} object.
   */
  public void addStep(String name) {
    long time = System.currentTimeMillis();
    timings.put(name, time - cursor);
    cursor = time;
  }
}
