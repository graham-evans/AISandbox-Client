package dev.aisandbox.client.scenarios.twisty.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Move {
  int cost;
  Map<String, String> cellMapping = new HashMap<>();
}
