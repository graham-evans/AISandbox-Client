package dev.aisandbox.client.scenarios.twisty.puzzles.tpmodel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@XStreamAlias("Loop")
public class Loop {

  @Getter List<Cell> cells = new ArrayList<>();

  public void removeCell(Cell c) {
    cells.remove(c);
  }

  @Override
  public String toString() {
    if (cells.isEmpty()) {
      return "Empty Loop";
    } else {
      return "Loop with " + cells.size() + " cells";
    }
  }
}
