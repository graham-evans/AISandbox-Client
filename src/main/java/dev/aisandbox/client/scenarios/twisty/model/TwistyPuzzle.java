package dev.aisandbox.client.scenarios.twisty.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "Puzzle")
public class TwistyPuzzle {
  Map<String, Cell> cells = new LinkedHashMap<>();
  Map<String, Move> moves = new HashMap<>();
  Map<Character, Face> faces = new HashMap<>();
}
