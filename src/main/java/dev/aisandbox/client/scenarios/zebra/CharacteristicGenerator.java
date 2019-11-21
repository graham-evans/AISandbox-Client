package dev.aisandbox.client.scenarios.zebra;

import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;

import dev.aisandbox.client.scenarios.zebra.vo.Characteristic;
import dev.aisandbox.client.scenarios.zebra.vo.CharacteristicObject;
import dev.aisandbox.client.scenarios.zebra.vo.Template;

public class CharacteristicGenerator {

    private static final Logger LOG = Logger.getLogger(CharacteristicGenerator.class.getName());

    public static Template createTemplate(Random rand,int characteristics,int houses) {
        Template t = null;
        try {
            XStream x = new XStream();
            // configure
            x.processAnnotations(Template.class);
            x.allowTypes(new Class[]{Template.class, Characteristic.class, CharacteristicObject.class});
            // read
            t = (Template) x.fromXML(CharacteristicGenerator.class.getResourceAsStream("template.xml"));
            // randomise the characteristics
            Collections.shuffle(t.getCharacteristics(), rand);
            // trim the extra characteristics
            while (t.getCharacteristics().size()>characteristics) {
                t.getCharacteristics().remove(0);
            }
            // for each characteristic, shuffle and trim the instances
            for (Characteristic c : t.getCharacteristics()) {
                Collections.shuffle(c.getInstances(),rand);
                while (c.getInstances().size()>houses) {
                    c.getInstances().remove(0);
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Error reading template",e);
        }
        return t;
    }
}