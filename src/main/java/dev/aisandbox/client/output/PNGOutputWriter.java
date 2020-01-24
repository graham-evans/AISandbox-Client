package dev.aisandbox.client.output;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

/**
 * A class to write each video frame as a PNG within a subdirectory.
 *
 * <p>A new directory will be created and each frame will be written as a file nnnnnnnn.PNG
 */
@Component
public class PNGOutputWriter implements FrameOutput {

  private long count = 0;
  private File source = null;

  /**
   * Return the descriptive name of this output writer.
   *
   * <p>This is used when Spring initialises all available implementations of {@link FrameOutput}
   * and displays them on the UI
   *
   * @param l a {@link java.util.Locale} object.
   * @return the descriptive name "Write to files (PNG)"
   */
  @Override
  public String getName(Locale l) {
    return "Write to files (PNG)";
  }

  /**
   * Create a new directory off the given root.
   *
   * @param baseDir The root directory - multiple runs will create multiple entries in this
   *     directory.
   * @throws IOException thrown if the directory can't be created, or if open is called twice
   *     without closing.
   */
  @Override
  public void open(File baseDir) throws IOException {
    // check if we're already open
    if (source != null) {
      throw new IOException("Trying to open a stream which is already open");
    }
    source = new File(baseDir, UUID.randomUUID().toString());
    source.mkdirs();
  }

  /**
   * Add a single frame as a PNG image.
   *
   * @param frame a {@link java.awt.image.BufferedImage} object.
   * @throws IOException thrown if the image can't be written
   */
  @Override
  public void addFrame(BufferedImage frame) throws IOException {
    if (source == null) {
      throw new IOException("Writing to unopen writer");
    } else {
      File fileOut = new File(source, String.format("%08d", count++) + ".png");
      ImageIO.write(frame, "PNG", fileOut);
    }
  }

  /**
   * Finish writing files and reset for next open command
   *
   * @throws IOException never thrown in this implementation.
   */
  @Override
  public void close() throws IOException {
    source = null;
    count = 0;
  }
}
