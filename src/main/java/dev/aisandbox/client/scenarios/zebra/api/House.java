package dev.aisandbox.client.scenarios.zebra.api;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class House {
  private int housenumber;
  private List<HouseCharacteristics> characteristics = new ArrayList<>();
}
