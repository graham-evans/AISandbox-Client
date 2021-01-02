package dev.aisandbox.client.scenarios.twisty;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

@Slf4j
public class BufferedImageConverter implements Converter {

  @Override
  public boolean canConvert(Class type) {
    return type.isAssignableFrom(BufferedImage.class);
  }

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    BufferedImage image = (BufferedImage) source;
    try {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      ImageIO.write(image, "png", buffer);
      buffer.close();
      writer.setValue(Base64.encodeBase64String(buffer.toByteArray()));
    } catch (IOException e) {
      log.error("Error encoding buffered image", e);
    }
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    BufferedImage image = null;
    try {
      byte[] buffer = Base64.decodeBase64(reader.getValue());
      ByteArrayInputStream in = new ByteArrayInputStream(buffer);
      image = ImageIO.read(in);
    } catch (IOException e) {
      log.error("Error decoding buffered image", e);
    }
    return image;
  }
}
