package dev.aisandbox.client.output.charts;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import lombok.extern.slf4j.Slf4j;

/**
 * GraphUtils class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
public class GraphUtils {

  // private constructor to stop the class from being initialised.
  private GraphUtils() {}

  /**
   * Setup the rendering hints on a graphics object
   *
   * @param g
   */
  private static void setupRenderingHints(Graphics2D g) {
    g.setRenderingHint(
        RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
  }
}
