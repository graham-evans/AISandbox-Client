package dev.aisandbox.client.scenarios.twisty.puzzles;

import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.scenarios.twisty.NotExistentMoveException;
import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Cube3x3x3 extends CubePuzzle implements TwistyPuzzle {

  // screen constants
  private static final int SCREEN_WIDTH = 1920;
  private static final int SCREEN_HEIGHT = 1080;
  private static final int SCREEN_TOP_MARGIN = 80;
  private static final int SCREEN_BOTTOM_MARGIN = 40;
  private static final int SCREEN_LEFT_MARGIN = 40;
  private static final int SCREEN_RIGHT_MARGIN = 40;
  private static final int FACE_SEPERATION = 20;
  // puzzle settings - once setup they dont get changed
  private final Map<Character, Color> colorMap = new HashMap<>();
  private final Map<String, Polygon> cells = new HashMap<>();
  private final String solvedState;
  private final List<String> moveList;
  private final Map<String, BufferedImage> moveImageMap = new HashMap<>();
  // state settings
  private Map<String, Character> currentState = new LinkedHashMap<>();

  private final Map<Character, Character> faceMap =
      Map.of('U', 'W', 'D', 'Y', 'F', 'G', 'B', 'B', 'R', 'R', 'L', 'O');

  private static final int size = 3;

  public Cube3x3x3() {
    // populate colour Map
    populateColours();
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
  }

  private void populateColours() {
    colorMap.put('W', Color.WHITE);
    colorMap.put('G', Color.GREEN);
    colorMap.put('R', Color.RED);
    colorMap.put('B', Color.BLUE);
    colorMap.put('O', Color.ORANGE);
    colorMap.put('Y', Color.YELLOW);
    log.debug("Added {} colours", colorMap.size());
  }

  private void populateCells(int faceSize, int cellSize) {
    populateCellGrid(
        size,
        cellSize,
        SCREEN_LEFT_MARGIN + faceSize + FACE_SEPERATION,
        SCREEN_TOP_MARGIN,
        'U',
        'W');
    populateCellGrid(
        size,
        cellSize,
        SCREEN_LEFT_MARGIN,
        SCREEN_TOP_MARGIN + faceSize + FACE_SEPERATION,
        'L',
        'O');
    populateCellGrid(
        size,
        cellSize,
        SCREEN_LEFT_MARGIN + faceSize + FACE_SEPERATION,
        SCREEN_TOP_MARGIN + faceSize + FACE_SEPERATION,
        'F',
        'G');
    populateCellGrid(
        size,
        cellSize,
        SCREEN_LEFT_MARGIN + (faceSize + FACE_SEPERATION) * 2,
        SCREEN_TOP_MARGIN + faceSize + FACE_SEPERATION,
        'R',
        'R');
    populateCellGrid(
        size,
        cellSize,
        SCREEN_LEFT_MARGIN + (faceSize + FACE_SEPERATION) * 3,
        SCREEN_TOP_MARGIN + faceSize + FACE_SEPERATION,
        'B',
        'B');
    populateCellGrid(
        size,
        cellSize,
        SCREEN_LEFT_MARGIN + faceSize + FACE_SEPERATION,
        SCREEN_TOP_MARGIN + (faceSize + FACE_SEPERATION) * 2,
        'D',
        'Y');
    log.info("Finished creating cells");
  }

  private void populateCellGrid(
      int size, int length, int startX, int startY, char face, char colour) {
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
  public String getPuzzleName() {
    return "Cube 3x3x3 (OBTM)";
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

  @Override
  public int applyMove(String move) throws NotExistentMoveException {
    switch (move) {
      case "F":
        applyFaceMove('F');
        applyBlockMove('F', 1);
        return 1;
      case "F2":
        applyMove("F");
        applyMove("F");
        return 1;
      case "F'":
        applyMove("F");
        applyMove("F");
        applyMove("F");
        return 1;
      case "Fw":
        applyFaceMove('F');
        applyBlockMove('F', 2);
        return 1;
      case "Fw2":
        applyMove("Fw");
        applyMove("Fw");
        return 1;
      case "Fw'":
        applyMove("Fw");
        applyMove("Fw");
        applyMove("Fw");
        return 1;
      case "L":
        applyFaceMove('L');
        applyBlockMove('L', 1);
        return 1;
      case "L'":
        applyMove("L");
        applyMove("L");
        applyMove("L");
        return 1;
      case "L2":
        applyMove("L");
        applyMove("L");
        return 1;
      case "Lw":
        applyFaceMove('L');
        applyBlockMove('L', 2);
        return 1;
      case "Lw'":
        applyMove("Lw");
        applyMove("Lw");
        applyMove("Lw");
        return 1;
      case "Lw2":
        applyMove("Lw");
        applyMove("Lw");
        return 1;
      case "R":
        applyFaceMove('R');
        applyBlockMove('R', 1);
        return 1;
      case "R'":
        applyMove("R");
        applyMove("R");
        applyMove("R");
        return 1;
      case "R2":
        applyMove("R");
        applyMove("R");
        return 1;
      case "Rw":
        applyFaceMove('R');
        applyBlockMove('R', 2);
        return 1;
      case "Rw'":
        applyMove("Rw");
        applyMove("Rw");
        applyMove("Rw");
        return 1;
      case "Rw2":
        applyMove("Rw");
        applyMove("Rw");
        return 1;
        // downward face moves
      case "D":
        applyFaceMove('D');
        applyBlockMove('D', 1);
        return 1;
      case "D'":
        applyMove("D");
        applyMove("D");
        applyMove("D");
        return 1;
      case "D2":
        applyMove("D");
        applyMove("D");
        return 1;
      case "Dw":
        applyFaceMove('D');
        applyBlockMove('D', 2);
        return 1;
      case "Dw'":
        applyMove("Dw");
        applyMove("Dw");
        applyMove("Dw");
        return 1;
      case "Dw2":
        applyMove("Dw");
        applyMove("Dw");
        return 1;
        // upward face moves
      case "U":
        applyFaceMove('U');
        applyBlockMove('U', 1);
        return 1;
      case "U'":
        applyMove("U");
        applyMove("U");
        applyMove("U");
        return 1;
      case "U2":
        applyMove("U");
        applyMove("U");
        return 1;
      case "Uw":
        applyFaceMove('U');
        applyBlockMove('U', 2);
        return 1;
      case "Uw'":
        applyMove("Uw");
        applyMove("Uw");
        applyMove("Uw");
        return 1;
      case "Uw2":
        applyMove("Uw");
        applyMove("Uw");
        return 1;
        // Back face moves
      case "B":
        applyFaceMove('B');
        applyBlockMove('B', 1);
        return 1;
      case "B'":
        applyMove("B");
        applyMove("B");
        applyMove("B");
        return 1;
      case "B2":
        applyMove("B");
        applyMove("B");
        return 1;
      case "Bw":
        applyFaceMove('B');
        applyBlockMove('B', 2);
        return 1;
      case "Bw'":
        applyMove("Bw");
        applyMove("Bw");
        applyMove("Bw");
        return 1;
      case "Bw2":
        applyMove("Bw");
        applyMove("Bw");
        return 1;
        // ROTATIONS
      case "x":
        applyFaceMove('R');
        applyFaceMove('L');
        applyFaceMove('L');
        applyFaceMove('L');
        applyBlockMove('R', size);
        return 0;
      case "y":
        applyFaceMove('U');
        applyFaceMove('D');
        applyFaceMove('D');
        applyFaceMove('D');
        applyBlockMove('U', size);
        return 0;
      case "z":
        applyFaceMove('F');
        applyBlockMove('F', size);
        applyFaceMove('B');
        applyFaceMove('B');
        applyFaceMove('B');
        return 0;
      case "x2":
        applyMove("x");
        applyMove("x");
        return 0;
      case "y2":
        applyMove("y");
        applyMove("y");
        return 0;
      case "z2":
        applyMove("z");
        applyMove("z");
        return 0;
      case "x'":
        applyMove("x");
        applyMove("x");
        applyMove("x");
        return 0;
      case "y'":
        applyMove("y");
        applyMove("y");
        applyMove("y");
        return 0;
      case "z'":
        applyMove("z");
        applyMove("z");
        applyMove("z");
        return 0;
      default:
        log.warn("Move '{}' doesn't exist", move);
        throw new NotExistentMoveException(move);
    }
  }

  private void applyFaceMove(char face) {
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

  private void applyBlockMove(char face, int targetDepth) {
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

  @Override
  public boolean isSolved() {
    return getState().equals(solvedState);
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
  public BufferedImage getMoveImage(String move) {

    int WIDTH = 80;
    int HEIGHT = 120;

    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0, 0, WIDTH, HEIGHT);

    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    // Get the FontMetrics
    FontMetrics metrics = g.getFontMetrics(font);
    // Determine the X coordinate for the text
    int dx = (metrics.stringWidth(move)) / 2;
    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of
    // the screen)
    int dy = ((metrics.getHeight()) / 2) + metrics.getAscent();
    // Set the font
    g.setFont(font);
    g.setColor(Color.BLACK);
    // Draw the String
    g.drawString(move, WIDTH / 2 - dx, HEIGHT - 2);

    return image;
  }

  public final int MOVE_ICON_WIDTH = 60;
  public final int MOVE_ICON_HEIGHT = 100;

  public final int MOVE_SPRITESHEET_WIDTH = 6;

  @Override
  public BufferedImage getMoveSpriteSheet() {
    // generate sprite sheet with all the move icons
    int num = moveList.size();
    BufferedImage sheet =
        new BufferedImage(
            MOVE_SPRITESHEET_WIDTH * MOVE_ICON_WIDTH,
            MOVE_ICON_HEIGHT * (int) Math.ceil(num * 1.0 / MOVE_SPRITESHEET_WIDTH),
            BufferedImage.TYPE_INT_RGB);
    Graphics2D g = sheet.createGraphics();
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    g.setFont(font);
    // Get the FontMetrics
    FontMetrics metrics = g.getFontMetrics(font);
    for (int i = 0; i < moveList.size(); i++) {
      String move = moveList.get(i);
      int x = (i % MOVE_SPRITESHEET_WIDTH) * MOVE_ICON_WIDTH;
      int y = (i / MOVE_SPRITESHEET_WIDTH) * MOVE_ICON_HEIGHT;
      g.setColor(Color.white);
      g.fillRect(x, y, MOVE_ICON_WIDTH, MOVE_ICON_HEIGHT);
      // Determine the X coordinate for the text
      int dx = (metrics.stringWidth(move)) / 2;
      // Set the font
      g.setColor(Color.BLACK);
      // Draw the String
      g.drawString(move, x + dx, y + MOVE_ICON_HEIGHT - 2);
    }
    return sheet;
  }
}
