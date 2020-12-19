package dev.aisandbox.client.scenarios.twisty.puzzles;

import com.thoughtworks.xstream.XStream;
import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.scenarios.twisty.NotExistentMoveException;
import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import dev.aisandbox.client.scenarios.twisty.puzzles.tpmodel.Cell;
import dev.aisandbox.client.scenarios.twisty.puzzles.tpmodel.ColourEnum;
import dev.aisandbox.client.scenarios.twisty.puzzles.tpmodel.CompiledMove;
import dev.aisandbox.client.scenarios.twisty.puzzles.tpmodel.Move;
import dev.aisandbox.client.scenarios.twisty.puzzles.tpmodel.Puzzle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TPPuzzle implements TwistyPuzzle {

  private final Puzzle puzzle;
  private final String baseState;
  private final Map<Character, Color> colorMap;
  private String currentState;
  private final Map<Character, Set<Integer>> faces;
  private final String name;

  public TPPuzzle(String tpResourceName) {
    log.info("Creating TP Puzzle based on {}", tpResourceName);
    name = tpResourceName.replaceAll(".*/", "");
    // load TP Puzzle Object
    XStream xstream = TPPuzzleCodec.getCodec();
    puzzle = (Puzzle) xstream.fromXML(TPPuzzle.class.getResourceAsStream(tpResourceName));
    log.info(
        "Loaded puzzle with {} cells and {} moves {}",
        puzzle.getCells().size(),
        puzzle.getMoves().size(),
        puzzle.getCompiledMoves().keySet());
    // compile moves
    // work out initial state
    StringBuilder stringBuilder = new StringBuilder();
    for (Cell c : puzzle.getCells()) {
      stringBuilder.append(c.getColour().getCharacter());
    }
    baseState = stringBuilder.toString();
    currentState = baseState;
    // create the default colour map
    colorMap = new HashMap<>();
    for (ColourEnum colour : ColourEnum.values()) {
      colorMap.put(colour.getCharacter(), colour.getAwtColour());
    }
    // extract faces
    faces = new HashMap<>();
    for (int i = 0; i < puzzle.getCells().size(); i++) {
      Cell cell = puzzle.getCells().get(i);
      char c = cell.getColour().getCharacter();
      if (faces.containsKey(c)) {
        faces.get(c).add(i);
      } else {
        Set<Integer> face = new HashSet<>();
        face.add(i);
        faces.put(c, face);
      }
    }
  }

  @Override
  public String getPuzzleName() {
    return "TP(" + name + ")";
  }

  @Override
  public void resetPuzzle() {
    currentState = baseState;
  }

  @Override
  public void resetPuzzle(String state) {
    currentState = state;
  }

  @Override
  public Map<Character, Color> getColourMap() {
    return colorMap;
  }

  @Override
  public String getState() {
    return currentState;
  }

  @Override
  public List<String> getMoveList() {
    return List.copyOf(puzzle.getCompiledMoves().keySet());
  }

  @Override
  public int applyMove(String name) throws NotExistentMoveException {
    CompiledMove move = puzzle.getCompiledMoves().get(name);
    if (move == null) {
      throw new NotExistentMoveException(name + " doesn't exist");
    }
    currentState = move.applyMove(currentState);
    return 1;
  }

  @Override
  public boolean isSolved() {
    for (Set<Integer> face : faces.values()) {
      // check all these cells have the same colour (dont care which).
      char c = ' ';
      for (Integer i : face) {
        if (' ' == c) {
          c = currentState.charAt(i);
        } else if (c != currentState.charAt(i)) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public BufferedImage getStateImage() {
    BufferedImage image = OutputTools.getWhiteScreen();
    Graphics2D g = image.createGraphics();
    for (int i = 0; i < puzzle.getCells().size(); i++) {
      Cell cell = puzzle.getCells().get(i);
      char state = currentState.charAt(i);
      Polygon polygon = cell.getPolygon();
      g.setColor(colorMap.get(state));
      g.fillPolygon(polygon);
      g.setColor(Color.LIGHT_GRAY);
      g.drawPolygon(polygon);
    }
    return image;
  }

  @Override
  public BufferedImage getMoveImage(String move) {
    return puzzle.getCompiledMoves().get(move).getImage();
  }

  @Override
  public BufferedImage createMoveSpriteSheet() {
    // work out the image size
    int rows = puzzle.getMoves().size() / 6;
    if (puzzle.getMoves().size() % 6 > 0) {
      rows++;
    }
    BufferedImage image =
        new BufferedImage(
            Move.MOVE_ICON_WIDTH * 6, Move.MOVE_ICON_HEIGHT * rows, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();
    for (int i = 0; i < puzzle.getMoves().size(); i++) {
      int x = i % 6;
      int y = i / 6;
      g.drawImage(
          puzzle.getMoves().get(i).getImageIcon(),
          x * Move.MOVE_ICON_WIDTH,
          y * Move.MOVE_ICON_HEIGHT,
          null);
    }
    return image;
  }
}
