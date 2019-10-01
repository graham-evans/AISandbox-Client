package dev.aisandbox.client.output;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * <p>FrameOutput interface.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
public interface FrameOutput {

    /**
     * <p>getName.</p>
     *
     * @param l a {@link java.util.Locale} object.
     * @return a {@link java.lang.String} object.
     */
    public String getName(Locale l);

    /**
     * <p>open.</p>
     *
     * @param baseDir a {@link java.io.File} object.
     * @throws java.io.IOException if any.
     */
    public void open(File baseDir) throws IOException;

    /**
     * <p>addFrame.</p>
     *
     * @param frame a {@link java.awt.image.BufferedImage} object.
     * @throws java.io.IOException if any.
     */
    public void addFrame(BufferedImage frame) throws IOException;

    /**
     * <p>close.</p>
     *
     * @throws java.io.IOException if any.
     */
    public void close() throws IOException;
}
