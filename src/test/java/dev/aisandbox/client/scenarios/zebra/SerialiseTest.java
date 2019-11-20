package dev.aisandbox.client.scenarios.zebra;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.thoughtworks.xstream.XStream;



public class SerialiseTest{
    @Test
    public void serialise() {
        Template t = CharacteristicGenerator.createTemplate(10, 10);

        // serialise this
        try {
            XStream x = new XStream();
            // configure
            // write
            PrintWriter out = new PrintWriter(new FileWriter("template.xml"));
            x.toXML(t, out);
            out.close();
            File file = new File("template.xml");
            assertNotNull(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}