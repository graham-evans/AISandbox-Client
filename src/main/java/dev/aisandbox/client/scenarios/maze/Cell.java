package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.scenarios.maze.api.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;

@RequiredArgsConstructor
public class Cell {

    @Getter
    private final int positionX;
    @Getter
    private final int positionY;

    @Getter
    Map<Direction, Cell> neighbours = new HashMap<>();

    @Getter
    Set<Direction> paths = new HashSet<>();

    @Getter
    @Setter
    private float value;

    public Position getPosition() {
        return new Position(positionX, positionY);
    }

    public void link(Direction d, Cell c) {
        if (c != null) {
            neighbours.put(d, c);
        }
    }

    public void linkBi(Direction d, Cell c) {
        if (c != null) {
            neighbours.put(d, c);
            c.getNeighbours().put(d.opposite(), this);
        }
    }

    public Collection<Cell> getLinks() {
        return neighbours.values();
    }

    public boolean isLinked(Cell c) {
        return neighbours.containsValue(c);
    }

    public void disconnect() {
        Iterator<Map.Entry<Direction, Cell>> itr = neighbours.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Direction, Cell> entry = itr.next();
            entry.getValue().getNeighbours().remove(entry.getKey().opposite(), this);
            itr.remove();
        }
    }

    public void addPath(Direction direction) {
        paths.add(direction);
        getNeighbours().get(direction).getPaths().add(direction.opposite());
    }

    public void addPath(Cell c) {
        Iterator<Map.Entry<Direction, Cell>> itr = neighbours.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Direction, Cell> entry = itr.next();
            if (entry.getValue() == c) {
                addPath(entry.getKey());
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return positionX == cell.positionX &&
                positionY == cell.positionY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionX, positionY);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "positionX=" + positionX +
                ", positionY=" + positionY +
                ", paths=" + paths +
                ", value=" + value +
                '}';
    }
}
