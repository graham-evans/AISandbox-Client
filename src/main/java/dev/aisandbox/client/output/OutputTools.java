package dev.aisandbox.client.output;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * OutputTools class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class OutputTools {
  /** Constant <code>VIDEO_WIDTH=1920</code> */
  public static final int VIDEO_WIDTH = 1920;
  /** Constant <code>VIDEO_HEIGHT=1080</code> */
  public static final int VIDEO_HEIGHT = 1080;

  private OutputTools() {}

  /**
   * getBlankScreen.
   *
   * @return a {@link java.awt.image.BufferedImage} object.
   */
  public static BufferedImage getBlankScreen() {
    return new BufferedImage(VIDEO_WIDTH, VIDEO_HEIGHT, BufferedImage.TYPE_INT_ARGB);
  }

  /**
   * getWhiteScreen.
   *
   * @return a {@link java.awt.image.BufferedImage} object.
   */
  public static BufferedImage getWhiteScreen() {
    return getColouredScreen(Color.WHITE);
  }

  /**
   * getBlackScreen.
   *
   * @return a {@link java.awt.image.BufferedImage} object.
   */
  public static BufferedImage getBlackScreen() {
    return getColouredScreen(Color.BLACK);
  }

  /**
   * getColouredScreen.
   *
   * @param color a {@link java.awt.Color} object.
   * @return a {@link java.awt.image.BufferedImage} object.
   */
  public static BufferedImage getColouredScreen(Color color) {
    BufferedImage image = new BufferedImage(VIDEO_WIDTH, VIDEO_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();
    g.setColor(color);
    g.fillRect(0, 0, VIDEO_WIDTH, VIDEO_HEIGHT);
    return image;
  }
}
