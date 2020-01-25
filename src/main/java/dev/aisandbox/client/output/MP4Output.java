package dev.aisandbox.client.output;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * MP4Output class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
public class MP4Output implements FrameOutput {

  private static final Logger LOG = LoggerFactory.getLogger(MP4Output.class.getName());

  private SeekableByteChannel out = null;
  private AWTSequenceEncoder encoder;

  /** {@inheritDoc} */
  @Override
  public String getName(Locale l) {
    return "Write to video (MP4)";
  }

  /** {@inheritDoc} */
  @Override
  public void open(File baseDir) throws IOException {
    File outputFile = new File(baseDir, UUID.randomUUID().toString() + ".mp4");
    try {
      out = NIOUtils.writableFileChannel(outputFile.getAbsolutePath());
      encoder = new AWTSequenceEncoder(out, Rational.R(25, 1));
    } catch (Exception e) {
      LOG.warn("Error setting up the output", e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void addFrame(BufferedImage frame) throws IOException {
    encoder.encodeImage(frame);
  }

  /** {@inheritDoc} */
  @Override
  public void close() throws IOException {
    encoder.finish();
    NIOUtils.closeQuietly(out);
  }
}
