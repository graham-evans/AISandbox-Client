package dev.aisandbox.client.scenarios.twisty.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Face {
  List<String> cells = new ArrayList<>();
  Integer requiredColor = null;
}
