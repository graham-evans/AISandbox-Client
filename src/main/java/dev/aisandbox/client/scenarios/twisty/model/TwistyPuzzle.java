package dev.aisandbox.client.scenarios.twisty.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Data;

@Data
@XmlRootElement(name = "Puzzle")
@XmlType(propOrder = {"colour", "cells", "moves", "faces"})
public class TwistyPuzzle {
  List<String> colour = new ArrayList<>();
  Map<String, Cell> cells = new HashMap<>();
  Map<String, Move> moves = new HashMap<>();
  List<Face> faces = new ArrayList<>();
}
