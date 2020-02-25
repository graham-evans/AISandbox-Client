package dev.aisandbox.client.scenarios.twisty.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
public class Cell {
  @XmlElement(name = "currentColour")
  int initialColour;

  List<Position> points = new ArrayList<>();
}
