package dev.aisandbox.client.scenarios.snake.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
  private int x, y;

  public Location nextStep(SnakeDirection direction) {
    Location l2 = new Location(x, y);
    switch (direction) {
      case NORTH:
        l2.setY(y - 1);
        break;
      case EAST:
        l2.setX(x + 1);
        break;
      case SOUTH:
        l2.setY(y + 1);
        break;
      case WEST:
        l2.setX(x - 1);
    }
    return l2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Location location = (Location) o;

    return new EqualsBuilder().append(x, location.x).append(y, location.y).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(x).append(y).toHashCode();
  }
}
