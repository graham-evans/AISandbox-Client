package dev.aisandbox.client.output.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * GraphUtils class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
public class GraphUtils {

  private static void setupRenderingHints(Graphics2D g) {
    boolean useDefault = true;
    try {
      // get the rendering hints from the OS
      Map<?, ?> desktopHints =
          (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
      if (desktopHints != null) {
        g.setRenderingHints(desktopHints);
        useDefault = false;
      }
    } catch (Exception e) {
      log.warn("Error getting desktop rendering hints - headless?", e);
      useDefault = false;
    }
    if (useDefault) {
      g.setRenderingHint(
          RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
      g.setRenderingHint(
          RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
    }
  }

  /**
   * getTitle.
   *
   * @param text a {@link java.lang.String} object.
   * @param font a {@link java.awt.Font} object.
   * @return a {@link java.awt.image.BufferedImage} object.
   */
  public static BufferedImage getTitle(String text, Font font) {
    // create fake image
    BufferedImage fake = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    // Get the FontMetrics
    Graphics2D fg = fake.createGraphics();
    // get the rendering hints from the OS
    Map<?, ?> desktopHints =
        (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
    if (desktopHints != null) {
      fg.setRenderingHints(desktopHints);
    }
    setupRenderingHints(fg);

    FontMetrics metrics = fg.getFontMetrics(font);
    // get the height of a line
    int height = metrics.getHeight();
    // get the width of the text
    int width = metrics.stringWidth(text);
    // get ascent and descent info
    int ascent = metrics.getAscent();
    int descent = metrics.getDescent();
    log.info("Height={}, width={}, ascent={},descent={}", height, width, ascent, descent);
    // now create the real image
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics2D = image.createGraphics();
    setupRenderingHints(graphics2D);
    graphics2D.setColor(Color.BLACK);
    graphics2D.setFont(font);
    graphics2D.drawString(text, 0, height - descent);
    return image;
  }
}
