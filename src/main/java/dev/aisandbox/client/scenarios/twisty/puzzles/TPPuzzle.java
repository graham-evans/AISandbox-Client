package dev.aisandbox.client.scenarios.twisty.puzzles;

import com.thoughtworks.xstream.XStream;
import dev.aisandbox.client.scenarios.twisty.NotExistentMoveException;
import dev.aisandbox.client.scenarios.twisty.TwistyPuzzle;
import dev.aisandbox.client.scenarios.twisty.puzzles.tpmodel.Cell;
import dev.aisandbox.client.scenarios.twisty.puzzles.tpmodel.Puzzle;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TPPuzzle implements TwistyPuzzle {

  private final Puzzle puzzle;
  private final String baseState;

  public TPPuzzle(String tpResourceName) {
    log.info("Creating TP Puzzle based on {}", tpResourceName);
    // load TP Puzzle Object
    XStream xstream = new XStream();
    xstream.processAnnotations(Puzzle.class);
    xstream.processAnnotations(Cell.class);
    puzzle = (Puzzle) xstream.fromXML(TPPuzzle.class.getResourceAsStream(tpResourceName));
    log.info(
        "Loaded puzzle with {} cells and {} moves",
        puzzle.getCells().size(),
        puzzle.getMoves().size());
    // compile moves
    // work out initial state
    StringBuilder stringBuilder = new StringBuilder();
    for (Cell c : puzzle.getCells()) {
      stringBuilder.append(c.getColour().getCharacter());
    }
    baseState = stringBuilder.toString();
  }

  @Override
  public String getPuzzleName() {
    return null;
  }

  @Override
  public void resetPuzzle() {}

  @Override
  public void resetPuzzle(String state) {}

  @Override
  public Map<Character, Color> getColourMap() {
    return null;
  }

  @Override
  public String getState() {
    return null;
  }

  @Override
  public List<String> getMoveList() {
    return null;
  }

  @Override
  public int applyMove(String move) throws NotExistentMoveException {
    return 0;
  }

  @Override
  public boolean isSolved() {
    return false;
  }

  @Override
  public BufferedImage getStateImage() {
    return null;
  }

  @Override
  public BufferedImage getMoveImage(String move) {
    return null;
  }

  @Override
  public BufferedImage createMoveSpriteSheet() {
    return null;
  }
}
