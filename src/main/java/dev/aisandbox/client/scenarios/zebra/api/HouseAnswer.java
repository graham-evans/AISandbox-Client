package dev.aisandbox.client.scenarios.zebra.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HouseAnswer {
  private Integer houseNumber = null;
  private List<HouseAttribute> attributes = new ArrayList<HouseAttribute>();
}
