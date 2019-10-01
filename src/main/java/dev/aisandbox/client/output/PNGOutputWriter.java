package dev.aisandbox.client.output;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

/**
 * <p>PNGOutputWriter class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
public class PNGOutputWriter implements FrameOutput {

    private long count = 0;
    private File source = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName(Locale l) {
        return "Write to files (PNG)";
    }

    /** {@inheritDoc} */
    @Override
    public void open(File baseDir) throws IOException {
        source = new File(baseDir, UUID.randomUUID().toString());
        source.mkdirs();
    }

    /** {@inheritDoc} */
    @Override
    public void addFrame(BufferedImage frame) throws IOException {
        if (source == null) {
            throw new IOException("Writing to unopen writer");
        } else {
            File fileOut = new File(source, String.format("%08d", count++) + ".png");
            ImageIO.write(frame, "PNG", fileOut);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void close() throws IOException {
        source = null;
    }

}
