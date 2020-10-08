package dev.aisandbox.client.scenarios.zebra;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import dev.aisandbox.client.scenarios.zebra.vo.Characteristic;
import dev.aisandbox.client.scenarios.zebra.vo.CharacteristicObject;
import dev.aisandbox.client.scenarios.zebra.vo.Template;
import java.util.Collections;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CharacteristicGenerator class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class CharacteristicGenerator {

  private static final Logger LOG =
      LoggerFactory.getLogger(CharacteristicGenerator.class.getName());

  private static XStream createXStream() {
    XStream xstream = new XStream();
    xstream.processAnnotations(Template.class);
    xstream.processAnnotations(Characteristic.class);
    xstream.processAnnotations(CharacteristicObject.class);
    // setup proper security by limiting which classes can be loaded by XStream
    xstream.addPermission(NoTypePermission.NONE);
    xstream.processAnnotations(Template.class);
    xstream.allowTypes(
        new Class[] {Template.class, Characteristic.class, CharacteristicObject.class});
    return xstream;
  }

  /**
   * createTemplate.
   *
   * @param rand a {@link java.util.Random} object.
   * @param characteristics a int.
   * @param houses a int.
   * @return a {@link dev.aisandbox.client.scenarios.zebra.vo.Template} object.
   */
  public static Template createTemplate(Random rand, int characteristics, int houses) {
    Template t = null;
    try {
      XStream x = createXStream();
      // read
      t = (Template) x.fromXML(CharacteristicGenerator.class.getResourceAsStream("template.xml"));
      // randomise the characteristics
      Collections.shuffle(t.getCharacteristics(), rand);
      // trim the extra characteristics
      while (t.getCharacteristics().size() > characteristics) {
        t.getCharacteristics().remove(0);
      }
      // for each characteristic, shuffle and trim the instances
      for (Characteristic c : t.getCharacteristics()) {
        Collections.shuffle(c.getInstances(), rand);
        while (c.getInstances().size() > houses) {
          c.getInstances().remove(0);
        }
      }
    } catch (Exception e) {
      LOG.error("Error reading template", e);
    }
    return t;
  }
}
