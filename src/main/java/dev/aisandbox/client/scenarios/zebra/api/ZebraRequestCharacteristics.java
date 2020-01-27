package dev.aisandbox.client.scenarios.zebra.api;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ZebraRequestCharacteristics {
  private Integer characteristicNumber = null;
  private String characteristicName = null;
  private List<ZebraRequestEntries> entries = new ArrayList<>();
}
