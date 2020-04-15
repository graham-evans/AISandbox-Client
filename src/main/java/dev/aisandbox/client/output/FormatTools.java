package dev.aisandbox.client.output;

import lombok.experimental.UtilityClass;

/**
 * FormatTools class.
 *
 * @author gde
 * @version $Id: $Id
 */
@UtilityClass
public class FormatTools {
  /**
   * Format a time duration (in milliseconds) as HH:MM:SS.sss
   *
   * @param milliseconds a long.
   * @return a {@link java.lang.String} object.
   */
  public static String formatTime(long milliseconds) {
    long millis = milliseconds % 1000;
    long second = (milliseconds / 1000) % 60;
    long minute = (milliseconds / (1000 * 60)) % 60;
    long hour = (milliseconds / (1000 * 60 * 60));

    return String.format("%d:%02d:%02d.%03d", hour, minute, second, millis);
  }
}
