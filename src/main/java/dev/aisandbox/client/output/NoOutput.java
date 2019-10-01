package dev.aisandbox.client.output;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * <p>NoOutput class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
public class NoOutput implements FrameOutput {
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName(Locale l) {
        return "No Output";
    }

    /** {@inheritDoc} */
    @Override
    public void open(File baseDir) throws IOException {
        // do nothing
    }

    /** {@inheritDoc} */
    @Override
    public void addFrame(BufferedImage frame) throws IOException {
        // do nothing
    }

    /** {@inheritDoc} */
    @Override
    public void close() throws IOException {
        // do nothing
    }
}
