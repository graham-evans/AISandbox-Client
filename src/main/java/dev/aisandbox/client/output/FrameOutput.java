package dev.aisandbox.client.output;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * FrameOutput interface.
 *
 * @author gde
 * @version $Id: $Id
 */
public interface FrameOutput {

  /**
   * getName.
   *
   * @param l a {@link java.util.Locale} object.
   * @return a {@link java.lang.String} object.
   */
  public String getName(Locale l);

  /**
   * open.
   *
   * @param baseDir a {@link java.io.File} object.
   * @throws java.io.IOException if any.
   */
  public void open(File baseDir) throws IOException;

  /**
   * addFrame.
   *
   * @param frame a {@link java.awt.image.BufferedImage} object.
   * @throws java.io.IOException if any.
   */
  public void addFrame(BufferedImage frame) throws IOException;

  /**
   * close.
   *
   * @throws java.io.IOException if any.
   */
  public void close() throws IOException;
}
