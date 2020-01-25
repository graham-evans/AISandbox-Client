package dev.aisandbox.client.scenarios.zebra.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class House {
  private int housenumber;
  private List<HouseCharacteristics> characteristics = new ArrayList<>();
}
