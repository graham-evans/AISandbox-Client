package dev.aisandbox.client.output;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class NoOutput implements FrameOutput {
    @Override
    public String getName(Locale l) {
        return "No Output";
    }

    @Override
    public void open(File baseDir) throws IOException {
        // do nothing
    }

    @Override
    public void addFrame(BufferedImage frame) throws IOException {
        // do nothing
    }

    @Override
    public void close() throws IOException {
        // do nothing
    }
}
