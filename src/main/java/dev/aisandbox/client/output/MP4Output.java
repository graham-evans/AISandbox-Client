package dev.aisandbox.client.output;

import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;
import org.springframework.stereotype.Component;

import org.jcodec.api.awt.AWTSequenceEncoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MP4Output implements FrameOutput {

    Logger LOG = Logger.getLogger(MP4Output.class.getName());

    SeekableByteChannel out = null;
    AWTSequenceEncoder encoder;

    @Override
    public String getName(Locale l) {
        return "Write to video (MP4)";
    }

    @Override
    public void open(File baseDir) throws IOException {
        File outputFile = new File(baseDir, UUID.randomUUID().toString() + ".mp4");
        try {
            out = NIOUtils.writableFileChannel(outputFile.getAbsolutePath());
            encoder = new AWTSequenceEncoder(out, Rational.R(25, 1));
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error setting up the output", e);
        }
    }

    @Override
    public void addFrame(BufferedImage frame) throws IOException {
        encoder.encodeImage(frame);
    }

    @Override
    public void close() throws IOException {
        encoder.finish();
        NIOUtils.closeQuietly(out);
    }
}
