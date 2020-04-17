package dev.aisandbox.client.output;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;
import org.springframework.stereotype.Component;

/**
 * MP4Output class.
 */
@Component
@Slf4j
public class MP4Output implements FrameOutput {

  private SeekableByteChannel out = null;
  private AWTSequenceEncoder encoder;

  /**
   * Get the name of this output option
   * @param l a {@link java.util.Locale} object.
   * @return The name of the option to be shown in the UI
   */
  @Override
  public String getName(Locale l) {
    return "Write to video (MP4)";
  }

  /**
   * Open a new MP4 file in the base directory
   * @param baseDir a {@link java.io.File} object.
   * @throws IOException
   */
  @Override
  public void open(File baseDir) throws IOException {
    File outputFile = new File(baseDir, UUID.randomUUID().toString() + ".mp4");
    try {
      out = NIOUtils.writableFileChannel(outputFile.getAbsolutePath());
      encoder = new AWTSequenceEncoder(out, Rational.R(25, 1));
    } catch (Exception e) {
      log.warn("Error setting up the output", e);
    }
  }

  /**
   * Add a frame to the current MP$ movie.
   * @param frame a {@link java.awt.image.BufferedImage} object.
   * @throws IOException
   */
  @Override
  public void addFrame(BufferedImage frame) throws IOException {
    encoder.encodeImage(frame);
  }

  /**
   * Close the current movie file.
   * @throws IOException
   */
  @Override
  public void close() throws IOException {
    encoder.finish();
    NIOUtils.closeQuietly(out);
  }
}
