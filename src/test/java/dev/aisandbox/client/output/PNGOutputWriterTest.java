package dev.aisandbox.client.output;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class PNGOutputWriterTest {

    private static final Logger LOG = Logger.getLogger(PNGOutputWriterTest.class.getName());

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void openTest() throws IOException {
        // this should open a directory within the temp folder
        LOG.info("Opening dir in " + folder.getRoot().getAbsolutePath());
        PNGOutputWriter png = new PNGOutputWriter();
        png.open(folder.getRoot());
        // there should be a new directory
        assertEquals("One directory created", folder.getRoot().listFiles().length, 1);
    }

    @Test
    public void WriteTest() throws IOException {
        // open a new session then write three frames
        PNGOutputWriter png = new PNGOutputWriter();
        png.open(folder.getRoot());
        png.addFrame(OutputTools.getBlackScreen());
        png.addFrame(OutputTools.getWhiteScreen());
        png.addFrame(OutputTools.getColouredScreen(Color.RED));
        png.close();
        assertEquals("One directory created", folder.getRoot().listFiles().length, 1);
        assertEquals("Three files in subdirectory", folder.getRoot().listFiles()[0].listFiles().length, 3);
    }

    @Test(expected = IOException.class)
    public void writeToClosedTest() throws IOException {
        // try to write to a session before it's open
        PNGOutputWriter png = new PNGOutputWriter();
        png.addFrame(OutputTools.getWhiteScreen());
    }

    @Test(expected = IOException.class)
    public void doubleOpenTest() throws IOException {
        // open a new session then try and open it without closing
        PNGOutputWriter png = new PNGOutputWriter();
        png.open(folder.getRoot());
        png.addFrame(OutputTools.getBlackScreen());
        png.open(folder.getRoot()); // THIS SHOULD THROW AN EXCEPTION
        png.close();
    }

    @Test
    public void openCloseTest() throws IOException {
        // open a new session then try and open it without closing
        PNGOutputWriter png = new PNGOutputWriter();
        png.open(folder.getRoot());
        png.addFrame(OutputTools.getBlackScreen());
        png.close();
        png.open(folder.getRoot()); // No exception
        png.addFrame(OutputTools.getBlackScreen());
        png.close();
    }

    @Test
    public void nameTest() {
        PNGOutputWriter png = new PNGOutputWriter();
        assertNotNull("Null name", png.getName(Locale.UK));
        assertTrue("Short name", png.getName(Locale.UK).length() > 2);
    }

}
