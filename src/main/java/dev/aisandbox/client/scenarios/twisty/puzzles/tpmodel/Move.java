package dev.aisandbox.client.scenarios.twisty.puzzles.tpmodel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@XStreamAlias("move")
public class Move {
  @Getter @Setter String name;
  @Getter List<Loop> loops = new ArrayList<>();

  public void removeCell(Cell c) {
    for (Loop l : loops) {
      l.removeCell(c);
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append(" (");
    sb.append(loops.size());
    sb.append(")");
    return sb.toString();
  }
}
