package dev.aisandbox.client.output;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public interface FrameOutput {

    public String getName(Locale l);

    public void open(File baseDir) throws IOException;

    public void addFrame(BufferedImage frame) throws IOException;

    public void close() throws IOException;
}
