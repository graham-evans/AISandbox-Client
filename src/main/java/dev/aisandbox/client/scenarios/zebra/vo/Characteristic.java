package dev.aisandbox.client.scenarios.zebra.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@XStreamAlias("characteristic")
public class Characteristic {
  private String name;

  @XStreamImplicit(itemFieldName = "instance")
  private List<CharacteristicObject> instances = new ArrayList<>();
}
