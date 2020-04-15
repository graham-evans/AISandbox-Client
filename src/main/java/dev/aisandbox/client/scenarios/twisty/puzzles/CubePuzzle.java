package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.scenarios.twisty.NotExistentMoveException;
import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import dev.aisandbox.client.sprite.SpriteLoader;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CubePuzzle implements TwistyPuzzle {

  // screen constants
  private static final int SCREEN_WIDTH = 1920;
  private static final int SCREEN_HEIGHT = 1080;
  private static final int SCREEN_TOP_MARGIN = 80;
  private static final int SCREEN_BOTTOM_MARGIN = 40;
  private static final int SCREEN_LEFT_MARGIN = 40;
  private static final int SCREEN_RIGHT_MARGIN = 40;
  private static final int FACE_SEPERATION = 20;
  // move icons
  public static final int MOVE_ICON_WIDTH = 60;
  public static final int MOVE_ICON_HEIGHT = 100;
  public static final int MOVE_SPRITESHEET_WIDTH = 6;

  // puzzle settings - once setup they dont get changed
  private final int size;
  private final Map<Character, Color> colorMap = new HashMap<>();
  private final Map<String, Polygon> cells = new HashMap<>();
  private final String solvedState;
  private final List<String> moveList;
  private final Map<String, BufferedImage> moveImageMap = new HashMap<>();
  // state settings
  private Map<String, Character> currentState = new LinkedHashMap<>();

  /**
   * 
   * @param size the number of squares in each direction.
   * @param spritePath the resource name for the spritemap
   */
  public CubePuzzle(int size, String spritePath) {
    this.size = size;
    // populate colour Map
    colorMap.put('W', Color.WHITE);
    colorMap.put('G', Color.GREEN);
    colorMap.put('R', Color.RED);
    colorMap.put('B', Color.BLUE);
    colorMap.put('O', Color.ORANGE);
    colorMap.put('Y', Color.YELLOW);
    log.debug("Added {} colours", colorMap.size());
    // work out the size of the cells
    double faceSize =
        Math.floor(
            Math.min(
                (SCREEN_HEIGHT - SCREEN_TOP_MARGIN - SCREEN_BOTTOM_MARGIN - 2 * FACE_SEPERATION)
                    / 3.0,
                (SCREEN_WIDTH - SCREEN_LEFT_MARGIN - SCREEN_RIGHT_MARGIN - 3 * FACE_SEPERATION)
                    / 4.0));
    double cellSize = Math.floor(faceSize / size);
    // create the cells
    populateCells((int) faceSize, (int) cellSize);
    // remember the starting (solved) state
    solvedState = getState();
    // create the list of moves
    List<String> moves = new ArrayList<>();
    // add rotations - these are always the same for cubes
    moves.addAll(List.of("x", "x'", "y", "y'", "z", "z'", "x2", "y2", "z2"));
    // add face rotations
    moves.addAll(
        List.of(
            "U", "L", "F", "R", "B", "D", "U'", "L'", "F'", "R'", "B'", "D'", "U2", "L2", "F2",
            "R2", "B2", "D2"));
    // outer block rotations
    for (int depth = 2; depth < size; depth++) {
      moves.add((depth == 2 ? "" : depth) + "Fw");
      moves.add((depth == 2 ? "" : depth) + "Bw");
      moves.add((depth == 2 ? "" : depth) + "Uw");
      moves.add((depth == 2 ? "" : depth) + "Dw");
      moves.add((depth == 2 ? "" : depth) + "Lw");
      moves.add((depth == 2 ? "" : depth) + "Rw");

      moves.add((depth == 2 ? "" : depth) + "Fw'");
      moves.add((depth == 2 ? "" : depth) + "Bw'");
      moves.add((depth == 2 ? "" : depth) + "Uw'");
      moves.add((depth == 2 ? "" : depth) + "Dw'");
      moves.add((depth == 2 ? "" : depth) + "Lw'");
      moves.add((depth == 2 ? "" : depth) + "Rw'");

      moves.add((depth == 2 ? "" : depth) + "Fw2");
      moves.add((depth == 2 ? "" : depth) + "Bw2");
      moves.add((depth == 2 ? "" : depth) + "Uw2");
      moves.add((depth == 2 ? "" : depth) + "Dw2");
      moves.add((depth == 2 ? "" : depth) + "Lw2");
      moves.add((depth == 2 ? "" : depth) + "Rw2");
    }
    moveList = Collections.unmodifiableList(moves);
    log.debug("defined {} available moves", moveList.size());
    // load sprites for moves
    try {
      List<BufferedImage> images =
          SpriteLoader.loadSpritesFromResources(spritePath, MOVE_ICON_WIDTH, MOVE_ICON_HEIGHT);
      for (int i = 0; i < moveList.size(); i++) {
        String move = moveList.get(i);
        BufferedImage image = images.get(i);
        moveImageMap.put(move, image);
      }
    } catch (Exception e) {
      log.warn("Error loading move spritesheet", e);
    }
  }

  private void populateCells(int faceSize, int cellSize) {
    populateCellGrid(
        cellSize, SCREEN_LEFT_MARGIN + faceSize + FACE_SEPERATION, SCREEN_TOP_MARGIN, 'U', 'W');
    populateCellGrid(
        cellSize, SCREEN_LEFT_MARGIN, SCREEN_TOP_MARGIN + faceSize + FACE_SEPERATION, 'L', 'O');
    populateCellGrid(
        cellSize,
        SCREEN_LEFT_MARGIN + faceSize + FACE_SEPERATION,
        SCREEN_TOP_MARGIN + faceSize + FACE_SEPERATION,
        'F',
        'G');
    populateCellGrid(
        cellSize,
        SCREEN_LEFT_MARGIN + (faceSize + FACE_SEPERATION) * 2,
        SCREEN_TOP_MARGIN + faceSize + FACE_SEPERATION,
        'R',
        'R');
    populateCellGrid(
        cellSize,
        SCREEN_LEFT_MARGIN + (faceSize + FACE_SEPERATION) * 3,
        SCREEN_TOP_MARGIN + faceSize + FACE_SEPERATION,
        'B',
        'B');
    populateCellGrid(
        cellSize,
        SCREEN_LEFT_MARGIN + faceSize + FACE_SEPERATION,
        SCREEN_TOP_MARGIN + (faceSize + FACE_SEPERATION) * 2,
        'D',
        'Y');
    log.info("Finished creating cells");
  }

  private void populateCellGrid(int length, int startX, int startY, char face, char colour) {
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        // generate cell ID
        String cellID = face + createAddress(x, y);
        // store state
        currentState.put(cellID, colour);
        // create polygon
        Polygon p = new Polygon();
        p.addPoint(startX + x * length, startY + y * length);
        p.addPoint(startX + (x + 1) * length, startY + y * length);
        p.addPoint(startX + (x + 1) * length, startY + (y + 1) * length);
        p.addPoint(startX + x * length, startY + (y + 1) * length);
        cells.put(cellID, p);
      }
    }
    log.debug("Added cell grid for {} now {} cells exist", face, cells.size());
  }

  private String createAddress(int x, int y) {
    return String.format("%02x", x) + String.format("%02x", y);
  }

  private String createAddress(char face, int x, int y) {
    return face + String.format("%02x", x) + String.format("%02x", y);
  }

  @Override
  public void resetPuzzle() {
    List<String> cellsInOrder = new ArrayList<>();
    cellsInOrder.addAll(currentState.keySet());
    for (int i = 0; i < cellsInOrder.size(); i++) {
      currentState.put(cellsInOrder.get(i), solvedState.charAt(i));
    }
  }

  /**
   * Force the state of the puzzle to match the supplied String.
   *
   * <p>Note: this does not check that only valid colours are used, this makes it useful for unit
   * testing the moves, but could cause exceptions if the image is generated.
   *
   * @param forcedState
   */
  @Override
  public void resetPuzzle(String forcedState) {
    List<String> cellsInOrder = new ArrayList<>();
    cellsInOrder.addAll(currentState.keySet());
    for (int i = 0; i < cellsInOrder.size(); i++) {
      currentState.put(cellsInOrder.get(i), forcedState.charAt(i));
    }
  }

  @Override
  public Map<Character, Color> getColourMap() {
    return colorMap;
  }

  @Override
  public String getState() {
    StringBuilder stateBuilder = new StringBuilder();
    currentState.forEach((name, colour) -> stateBuilder.append(colour));
    return stateBuilder.toString();
  }

  @Override
  public List<String> getMoveList() {
    return moveList;
  }

  protected void applyFaceMove(char face) {
    // take a copy of the current state
    Map<String, Character> oldState = new HashMap<>();
    oldState.putAll(currentState);
    // update the face
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        String source = face + createAddress(x, y);
        String dest = face + createAddress(size - 1 - y, x);
        currentState.put(dest, oldState.get(source));
      }
    }
  }

  protected void applyBlockMove(char face, int targetDepth) {
    // take a copy of the current state
    Map<String, Character> oldState = new HashMap<>();
    oldState.putAll(currentState);
    for (int depth = 0; depth < targetDepth; depth++) {
      for (int step = 0; step < size; step++) {
        switch (face) {
          case 'F':
            currentState.put(
                "U" + createAddress(step, size - depth - 1),
                oldState.get("L" + createAddress(size - depth - 1, size - step - 1)));
            currentState.put(
                "R" + createAddress(depth, step),
                oldState.get(("U" + createAddress(step, size - depth - 1))));
            currentState.put(
                "D" + createAddress(size - step - 1, depth),
                oldState.get("R" + createAddress(depth, step)));
            currentState.put(
                "L" + createAddress(size - depth - 1, size - step - 1),
                oldState.get("D" + createAddress(size - step - 1, depth)));
            break;
          case 'L':
            currentState.put(
                "U" + createAddress(depth, step),
                oldState.get("B" + createAddress(size - depth - 1, size - step - 1)));
            currentState.put(
                "F" + createAddress(depth, step), oldState.get("U" + createAddress(depth, step)));
            currentState.put(
                "D" + createAddress(depth, step), oldState.get("F" + createAddress(depth, step)));
            currentState.put(
                "B" + createAddress(size - depth - 1, size - step - 1),
                oldState.get("D" + createAddress(depth, step)));
            break;
          case 'R':
            currentState.put(
                createAddress('D', size - depth - 1, size - step - 1),
                oldState.get(createAddress('B', depth, step)));
            currentState.put(
                createAddress('F', size - depth - 1, size - step - 1),
                oldState.get(createAddress('D', size - depth - 1, size - step - 1)));
            currentState.put(
                createAddress('U', size - depth - 1, size - step - 1),
                oldState.get(createAddress('F', size - depth - 1, size - step - 1)));
            currentState.put(
                createAddress('B', depth, step),
                oldState.get(createAddress('U', size - depth - 1, size - step - 1)));
            break;
          case 'D':
            currentState.put(
                createAddress('L', step, size - depth - 1),
                oldState.get(createAddress('B', step, size - depth - 1)));
            currentState.put(
                createAddress('F', step, size - depth - 1),
                oldState.get(createAddress('L', step, size - depth - 1)));
            currentState.put(
                createAddress('R', step, size - depth - 1),
                oldState.get(createAddress('F', step, size - depth - 1)));
            currentState.put(
                createAddress('B', step, size - depth - 1),
                oldState.get(createAddress('R', step, size - depth - 1)));
            break;
          case 'U':
            currentState.put(
                createAddress('L', step, depth), oldState.get(createAddress('F', step, depth)));
            currentState.put(
                createAddress('F', step, depth), oldState.get(createAddress('R', step, depth)));
            currentState.put(
                createAddress('R', step, depth), oldState.get(createAddress('B', step, depth)));
            currentState.put(
                createAddress('B', step, depth), oldState.get(createAddress('L', step, depth)));
            break;
          case 'B':
            currentState.put(
                createAddress('U', size - step - 1, depth),
                oldState.get(createAddress('R', size - depth - 1, size - step - 1)));
            currentState.put(
                createAddress('L', depth, step),
                oldState.get(createAddress('U', size - step - 1, depth)));
            currentState.put(
                createAddress('D', step, size - depth - 1),
                oldState.get(createAddress('L', depth, step)));
            currentState.put(
                createAddress('R', size - depth - 1, size - step - 1),
                oldState.get(createAddress('D', step, size - depth - 1)));
            break;
          default:
            log.error("Unknown face applying block move on '" + face + "' depth " + depth);
        }
      }
    }
  }

  /**
   * A cube is solved if all sides are the same colour - rotation doesn't matter
   *
   * @return
   */
  @Override
  public boolean isSolved() {
    // iterate through all tiles matching the colour with other tiles on the same face
    Map<Character, Character> faceColourMap = new HashMap<>();
    Iterator<Entry<String, Character>> iterator = currentState.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, Character> entry = iterator.next();
      char face = entry.getKey().charAt(0);
      if (faceColourMap.containsKey(face)) {
        // we've already looked at this face
        if (!entry.getValue().equals(faceColourMap.get(face))) {
          // colour dont match - cube not solved
          return false;
        }
      } else {
        faceColourMap.put(face, entry.getValue());
      }
    }
    return true;
  }

  @Override
  public BufferedImage getStateImage() {
    BufferedImage image = OutputTools.getWhiteScreen();
    Graphics2D g = image.createGraphics();
    currentState.forEach(
        (cellID, colourID) -> {
          Polygon polygon = cells.get(cellID);
          g.setColor(colorMap.get(colourID));
          g.fillPolygon(polygon);
          g.setColor(Color.LIGHT_GRAY);
          g.drawPolygon(polygon);
        });
    return image;
  }

  @Override
  public BufferedImage createMoveSpriteSheet() {
    // generate sprite sheet with all the move icons
    int num = moveList.size();
    BufferedImage sheet =
        new BufferedImage(
            MOVE_SPRITESHEET_WIDTH * MOVE_ICON_WIDTH,
            MOVE_ICON_HEIGHT * (int) Math.ceil(num * 1.0 / MOVE_SPRITESHEET_WIDTH),
            BufferedImage.TYPE_INT_RGB);
    // work out how big to draw the cube
    int cubeBoxSize = (MOVE_ICON_WIDTH - 4) / size;
    int cubeMarginX = (MOVE_ICON_WIDTH - size * cubeBoxSize) / 2;
    int cubeMarginY = (MOVE_ICON_HEIGHT - 20 - size * cubeBoxSize) / 2;
    // load the background arrows
    List<BufferedImage> arrows = null;
    try {
      arrows =
          SpriteLoader.loadSpritesFromResources(
              "/dev/aisandbox/client/scenarios/twisty/arrows.png",
              MOVE_ICON_WIDTH,
              MOVE_ICON_HEIGHT);
    } catch (IOException e) {
      log.error("Error loading arrows", e);
    }

    Graphics2D g = sheet.createGraphics();
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    g.setFont(font);
    g.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    // Get the FontMetrics
    FontMetrics metrics = g.getFontMetrics(font);
    for (int i = 0; i < moveList.size(); i++) {
      String move = moveList.get(i);
      int x = (i % MOVE_SPRITESHEET_WIDTH) * MOVE_ICON_WIDTH;
      int y = (i / MOVE_SPRITESHEET_WIDTH) * MOVE_ICON_HEIGHT;
      g.setColor(Color.white);
      g.fillRect(x, y, MOVE_ICON_WIDTH, MOVE_ICON_HEIGHT);
      // Determine the X coordinate for the text
      int dx = (MOVE_ICON_WIDTH - metrics.stringWidth(move)) / 2;
      // Set the font
      g.setColor(Color.BLACK);
      // Draw the String
      g.drawString(move, x + dx, y + MOVE_ICON_HEIGHT - 4);
      // cube background
      g.setColor(Color.LIGHT_GRAY);
      if (move.matches(".*F.*")) {
        g.fillRect(x + cubeMarginX, y + cubeMarginY, size * cubeBoxSize, size * cubeBoxSize);
      }
      if (move.matches(".*U.*")) {
        int levels = parseLevels(move);
        g.fillRect(x + cubeMarginX, y + cubeMarginY, size * cubeBoxSize, levels * cubeBoxSize);
      }
      if (move.matches(".*R.*")) {
        int levels = parseLevels(move);
        g.fillRect(
            x + cubeMarginX + (size - levels) * cubeBoxSize,
            y + cubeMarginY,
            levels * cubeBoxSize,
            size * cubeBoxSize);
      }
      if (move.matches(".*L.*")) {
        int levels = parseLevels(move);
        g.fillRect(x + cubeMarginX, y + cubeMarginY, levels * cubeBoxSize, size * cubeBoxSize);
      }
      if (move.matches(".*D.*")) {
        int levels = parseLevels(move);
        g.fillRect(
            x + cubeMarginX,
            y + cubeMarginY + (size - levels) * cubeBoxSize,
            size * cubeBoxSize,
            levels * cubeBoxSize);
      }
      if (move.startsWith("x") || (move.startsWith("y")) || (move.startsWith("z"))) {
        g.fillRect(x + cubeMarginX, y + cubeMarginY, size * cubeBoxSize, size * cubeBoxSize);
      }
      // draw the cube
      g.setColor(Color.DARK_GRAY);
      for (int cx = 0; cx < size; cx++) {
        for (int cy = 0; cy < size; cy++) {
          g.drawRect(
              x + cx * cubeBoxSize + cubeMarginX,
              y + cy * cubeBoxSize + cubeMarginY,
              cubeBoxSize,
              cubeBoxSize);
        }
      }
      // draw arrows
      String arrow = move.replaceAll("[0-9w]", "");
      switch (arrow) {
        case "F":
          g.drawImage(arrows.get(12), x, y, null);
          break;
        case "F'":
          g.drawImage(arrows.get(13), x, y, null);
          break;
        case "B":
          g.drawImage(arrows.get(13), x, y, null);
          break;
        case "B'":
          g.drawImage(arrows.get(12), x, y, null);
          break;
        case "U":
          g.drawImage(arrows.get(7), x, y, null);
          break;
        case "U'":
          g.drawImage(arrows.get(6), x, y, null);
          break;
        case "R":
          g.drawImage(arrows.get(4), x, y, null);
          break;
        case "R'":
          g.drawImage(arrows.get(5), x, y, null);
          break;
        case "L":
          g.drawImage(arrows.get(9), x, y, null);
          break;
        case "L'":
          g.drawImage(arrows.get(8), x, y, null);
          break;
        case "D":
          g.drawImage(arrows.get(11), x, y, null);
          break;
        case "D'":
          g.drawImage(arrows.get(10), x, y, null);
          break;
        case "x":
          g.drawImage(arrows.get(0), x, y, null);
          break;
        case "x'":
          g.drawImage(arrows.get(1), x, y, null);
          break;
        case "y":
          g.drawImage(arrows.get(3), x, y, null);
          break;
        case "y'":
          g.drawImage(arrows.get(2), x, y, null);
          break;
        case "z":
          g.drawImage(arrows.get(12), x, y, null);
          break;
        case "z'":
          g.drawImage(arrows.get(13), x, y, null);
          break;
        default:
          log.warn("Can't draw arrow for move class '{}'", arrow);
      }
    }
    return sheet;
  }

  private static int parseLevels(String move) {
    int level = 1;
    if (move.matches(".*w.*")) {
      level = 2;
      String num = move.replaceAll("^([0-9]*).*", "$1");
      if (num.length() > 0) {
        level = Integer.parseInt(num);
      }
    }
    return level;
  }

  @Override
  public int applyMove(String move) throws NotExistentMoveException {
    if (moveList.indexOf(move) == -1) {
      // selected move isn't in the allowed list
      log.info("move '{}' isn't allowed", move);
    }
    // FACE MOVES
    if (move.matches("^[FBUDRL]$")) {
      char face = move.charAt(0);
      applyFaceMove(face);
      applyBlockMove(face, 1);
      return 1;
    }
    // FACE INVERSE MOVES
    if (move.matches("^[FBUDLR]'$")) {
      char face = move.charAt(0);
      applyFaceMove(face);
      applyFaceMove(face);
      applyFaceMove(face);
      applyBlockMove(face, 1);
      applyBlockMove(face, 1);
      applyBlockMove(face, 1);
      return 1;
    }
    // FACE DOUBLE MOVES
    if (move.matches("^[FBUDLR]2$")) {
      char face = move.charAt(0);
      applyFaceMove(face);
      applyFaceMove(face);
      applyBlockMove(face, 1);
      applyBlockMove(face, 1);
      return 1;
    }
    // FACE BLOCK 2 MOVES
    if (move.matches("^[FBUDLR]w$")) {
      char face = move.charAt(0);
      applyFaceMove(face);
      applyBlockMove(face, 2);
      return 1;
    }
    // FACE BLOCK 2 Inverse Moves
    if (move.matches("^[FBUDLR]w'$")) {
      char face = move.charAt(0);
      applyFaceMove(face);
      applyFaceMove(face);
      applyFaceMove(face);
      applyBlockMove(face, 2);
      applyBlockMove(face, 2);
      applyBlockMove(face, 2);
      return 1;
    }
    // FACE BLOCK 2 double Moves
    if (move.matches("^[FBUDLR]w2$")) {
      char face = move.charAt(0);
      applyFaceMove(face);
      applyFaceMove(face);
      applyBlockMove(face, 2);
      applyBlockMove(face, 2);
      return 1;
    }
    // DEEP BLOCK MOVES
    if (move.matches("^[0-9]+[FBUDRL]w")) {
      int depth = Integer.parseInt(move.replaceAll("[^0-9.]", ""));
      char face = move.charAt(move.length() - 2);
      applyFaceMove(face);
      applyBlockMove(face, depth);
      return 1;
    }
    // DEEP BLOCK inverse MOVES
    if (move.matches("^[0-9]+[FBUDRL]w'")) {
      String originalMove = move.substring(0, move.length() - 1);
      applyMove(originalMove);
      applyMove(originalMove);
      applyMove(originalMove);
      return 1;
    }
    // DEEP BLOCK inverse MOVES
    if (move.matches("^[0-9]+[FBUDRL]w2")) {
      String originalMove = move.substring(0, move.length() - 1);
      applyMove(originalMove);
      applyMove(originalMove);
      return 1;
    }

    // ROTATION X
    if (move.equals("x")) {
      applyFaceMove('R');
      applyFaceMove('L');
      applyFaceMove('L');
      applyFaceMove('L');
      applyBlockMove('R', size);
      return 0;
    }
    // ROTATION X'
    if (move.equals("x'")) {
      applyMove("x");
      applyMove("x");
      applyMove("x");
      return 0;
    }
    // ROTATION X2
    if (move.equals("x2")) {
      applyMove("x");
      applyMove("x");
      return 0;
    }
    // ROTATION Y
    if (move.equals("y")) {
      applyFaceMove('U');
      applyFaceMove('D');
      applyFaceMove('D');
      applyFaceMove('D');
      applyBlockMove('U', size);
      return 0;
    }
    // ROTATION y'
    if (move.equals("y'")) {
      applyMove("y");
      applyMove("y");
      applyMove("y");
      return 0;
    }
    // ROTATION Y2
    if (move.equals("y2")) {
      applyMove("y");
      applyMove("y");
      return 0;
    }
    // ROTATION Z
    if (move.equals("z")) {
      applyFaceMove('F');
      applyBlockMove('F', size);
      applyFaceMove('B');
      applyFaceMove('B');
      applyFaceMove('B');
      return 0;
    }
    // ROTATION Z'
    if (move.equals("z'")) {
      applyMove("z");
      applyMove("z");
      applyMove("z");
      return 0;
    }
    // ROTATION Z2
    if (move.equals("z2")) {
      applyMove("z");
      applyMove("z");
      return 0;
    }
    // move not implemented
    log.warn("Move '{}' isn't implemented", move);
    throw new NotExistentMoveException(move);
  }

  @Override
  public BufferedImage getMoveImage(String move) {
    return moveImageMap.get(move);
  }
}
