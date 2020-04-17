package dev.aisandbox.client.output;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * NoOutput class.
 *
 * <p>Does not write output to file.
 */
public class NoOutput implements FrameOutput {

  /**
   * Get the name of the output type.
   *
   * @param l a {@link java.util.Locale} object.
   * @return The name of the output
   */
  @Override
  public String getName(Locale l) {
    return "No Output";
  }

  /**
   * Start a new output.
   *
   * <p>Ignored
   *
   * @param baseDir a {@link java.io.File} object.
   * @throws IOException
   */
  @Override
  public void open(File baseDir) throws IOException {
    // do nothing
  }

  /**
   * Write a frame to the output.
   *
   * <p>Ignored
   *
   * @param frame a {@link java.awt.image.BufferedImage} object.
   * @throws IOException
   */
  @Override
  public void addFrame(BufferedImage frame) throws IOException {
    // do nothing
  }

  /**
   * Close the output.
   *
   * @throws IOException
   */
  @Override
  public void close() throws IOException {
    // do nothing
  }
}
