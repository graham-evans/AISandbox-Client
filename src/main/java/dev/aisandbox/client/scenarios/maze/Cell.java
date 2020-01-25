package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.scenarios.maze.api.Position;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Cell class.
 *
 * @author gde
 * @version $Id: $Id
 */
@RequiredArgsConstructor
public class Cell {

  @Getter private final int positionX;
  @Getter private final int positionY;

  @Getter EnumMap<Direction, Cell> neighbours = new EnumMap<>(Direction.class);

  @Getter Set<Direction> paths = new HashSet<>();

  @Getter @Setter private float value;

  /**
   * getPosition.
   *
   * @return a {@link dev.aisandbox.client.scenarios.maze.api.Position} object.
   */
  public Position getPosition() {
    return new Position(positionX, positionY);
  }

  /**
   * link.
   *
   * @param d a {@link dev.aisandbox.client.scenarios.maze.Direction} object.
   * @param c a {@link dev.aisandbox.client.scenarios.maze.Cell} object.
   */
  public void link(Direction d, Cell c) {
    if (c != null) {
      neighbours.put(d, c);
    }
  }

  /**
   * linkBi.
   *
   * @param d a {@link dev.aisandbox.client.scenarios.maze.Direction} object.
   * @param c a {@link dev.aisandbox.client.scenarios.maze.Cell} object.
   */
  public void linkBi(Direction d, Cell c) {
    if (c != null) {
      neighbours.put(d, c);
      c.getNeighbours().put(d.opposite(), this);
    }
  }

  /**
   * getLinks.
   *
   * @return a {@link java.util.Collection} object.
   */
  public Collection<Cell> getLinks() {
    return neighbours.values();
  }

  /**
   * isLinked.
   *
   * @param c a {@link dev.aisandbox.client.scenarios.maze.Cell} object.
   * @return a boolean.
   */
  public boolean isLinked(Cell c) {
    return neighbours.containsValue(c);
  }

  /** disconnect. */
  public void disconnect() {
    Iterator<Entry<Direction, Cell>> itr = neighbours.entrySet().iterator();
    while (itr.hasNext()) {
      Map.Entry<Direction, Cell> entry = itr.next();
      entry.getValue().getNeighbours().remove(entry.getKey().opposite(), this);
      itr.remove();
    }
  }

  /**
   * addPath.
   *
   * @param direction a {@link dev.aisandbox.client.scenarios.maze.Direction} object.
   */
  public void addPath(Direction direction) {
    paths.add(direction);
    getNeighbours().get(direction).getPaths().add(direction.opposite());
  }

  public boolean isPath(Direction direction) {
    return paths.contains(direction);
  }

  /**
   * addPath.
   *
   * @param c a {@link dev.aisandbox.client.scenarios.maze.Cell} object.
   */
  public void addPath(Cell c) {
    Iterator<Map.Entry<Direction, Cell>> itr = neighbours.entrySet().iterator();
    while (itr.hasNext()) {
      Map.Entry<Direction, Cell> entry = itr.next();
      if (entry.getValue() == c) {
        addPath(entry.getKey());
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cell cell = (Cell) o;
    return positionX == cell.positionX && positionY == cell.positionY;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(positionX, positionY);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "Cell{"
        + "positionX="
        + positionX
        + ", positionY="
        + positionY
        + ", paths="
        + paths
        + ", value="
        + value
        + '}';
  }
}
