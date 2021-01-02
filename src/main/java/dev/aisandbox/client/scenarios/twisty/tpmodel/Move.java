package dev.aisandbox.client.scenarios.twisty.tpmodel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@XStreamAlias("move")
public class Move {
  // move icons
  /** Constant <code>MOVE_ICON_WIDTH=60</code>. */
  public static final int MOVE_ICON_WIDTH = 60;
  /** Constant <code>MOVE_ICON_HEIGHT=100</code>. */
  public static final int MOVE_ICON_HEIGHT = 100;

  @Getter String name;
  @Getter List<Loop> loops = new ArrayList<>();
  @Getter int cost;

  @Getter
  private BufferedImage imageIcon =
      new BufferedImage(MOVE_ICON_WIDTH, MOVE_ICON_HEIGHT, BufferedImage.TYPE_INT_RGB);

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
