package dev.aisandbox.client.scenarios.maze.api;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a position in the maze.
 *
 * <p>Uses X,Y coordinates, with 0,0 located top left.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

  int x;
  int y;

  /**
   * Overridden equals definition.
   *
   * <p>Returns true if compared against a position object with the same X, Y values.
   *
   * @param o The object to compare to.
   * @return true if the object is a position with the same X, Y values.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Position position = (Position) o;
    return x == position.x && y == position.y;
  }

  /**
   * Overridden hashCode, uses the X and Y values.
   *
   * @return the hash of the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
