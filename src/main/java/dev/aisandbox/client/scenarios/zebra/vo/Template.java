package dev.aisandbox.client.scenarios.zebra.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@XStreamAlias("template")
@Data
public class Template {
  @XStreamImplicit(itemFieldName = "characteristic")
  private final List<Characteristic> characteristics = new ArrayList<>();

  /*    public void addCharacteristic(GenericCharacteristic c) {
      Characteristic target = new Characteristic();
      target.setName(c.getClass().getSimpleName());
      for (int i=0;i<10;i++) {
          CharacteristicObject o = new CharacteristicObject();
          o.setName(c.getItem(i));
          o.setIcon("undefined");
          o.setPositiveDescription(c.getDescription(i));
          o.setNegativeDescription(c.getNegativeDescription(i));
          o.setAlternativePositiveDescriptions(new String[] {c.getDescription(i)});
          o.setAlternativeNegativeDescription(new String[] {c.getNegativeDescription(i)});
          target.getInstances().add(o);
      }
      characteristics.add(target);
  }*/
}
