package dev.aisandbox.client.scenarios.zebra;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;

import dev.aisandbox.client.scenarios.zebra.vo.Template;

public class CharacteristicGenerator {

    private static final Logger LOG = Logger.getLogger(CharacteristicGenerator.class.getName());

    public static Template createTemplate(int characteristics,int houses) {
        Template t = null;
        try {
            XStream x = new XStream();
            // configure
            x.processAnnotations(Template.class);
            // read
            t = (Template) x.fromXML(CharacteristicGenerator.class.getResourceAsStream("template.xml"));
            // TODO Trim template down to the required dimentions
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Error reading template",e);
        }
        return t;
    }
}