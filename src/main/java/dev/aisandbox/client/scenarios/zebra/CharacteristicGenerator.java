package dev.aisandbox.client.scenarios.zebra;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.*;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import dev.aisandbox.client.scenarios.zebra.vo.Characteristic;
import dev.aisandbox.client.scenarios.zebra.vo.CharacteristicObject;
import dev.aisandbox.client.scenarios.zebra.vo.Template;
import java.io.Writer;
import java.util.Collections;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacteristicGenerator {

  private static final Logger LOG =
      LoggerFactory.getLogger(CharacteristicGenerator.class.getName());

  /**
   * Using XStream in modern JDKs is becoming problematic, as it uses private access methods (which
   * now throw warnings) To fix this we use the solution detailed here
   * https://github.com/x-stream/xstream/issues/101
   *
   * <p>A longer-term solution may be to move away from xstream.
   *
   * @return an initialised XStream serialiser/deserialiser which doesn't use restricted methods
   */
  private static XStream createXStream() {
    XStream xstream =
        new XStream(
            new StaxDriver() {
              @Override
              public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out, "    ");
              }
            }) {
          // only register the converters we need; other converters generate a private access
          // warning in the console on Java9+...
          @Override
          protected void setupConverters() {
            registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
            registerConverter(new IntConverter(), PRIORITY_NORMAL);
            registerConverter(new FloatConverter(), PRIORITY_NORMAL);
            registerConverter(new DoubleConverter(), PRIORITY_NORMAL);
            registerConverter(new LongConverter(), PRIORITY_NORMAL);
            registerConverter(new ShortConverter(), PRIORITY_NORMAL);
            registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
            registerConverter(new ByteConverter(), PRIORITY_NORMAL);
            registerConverter(new StringConverter(), PRIORITY_NORMAL);
            registerConverter(new DateConverter(), PRIORITY_NORMAL);
            registerConverter(new CollectionConverter(getMapper()), PRIORITY_NORMAL);
            registerConverter(
                new ReflectionConverter(getMapper(), getReflectionProvider()), PRIORITY_VERY_LOW);
          }
        };
    xstream.autodetectAnnotations(true);

    // setup proper security by limiting which classes can be loaded by XStream
    xstream.addPermission(NoTypePermission.NONE);
    xstream.processAnnotations(Template.class);
    xstream.allowTypes(
        new Class[] {Template.class, Characteristic.class, CharacteristicObject.class});
    return xstream;
  }

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
