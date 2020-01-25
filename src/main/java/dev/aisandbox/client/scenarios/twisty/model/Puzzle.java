package dev.aisandbox.client.scenarios.twisty.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Puzzle {
  // the name of the puzzle
  private String puzzleName;
  // an ordered list of colours
  private List<Color> palette = new ArrayList<>();
  // a map of pieces
  private Map<String, Piece> pieces = new HashMap<>();
  // the faces
  private List<Face> faces = new ArrayList<>();
  // the possible moves
  private Map<String, Move> moves = new HashMap<>();
}
