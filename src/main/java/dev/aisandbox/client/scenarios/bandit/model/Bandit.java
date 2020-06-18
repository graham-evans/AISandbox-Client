package dev.aisandbox.client.scenarios.bandit.model;

import java.util.Random;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Bandit {

  @Getter @Setter private double normal = 0.0;
  @Getter @Setter private double std = 1.0;

  private final Random rand;

  public Bandit(@NonNull Random rand, double normal, double std) {
    this.rand = rand;
    this.normal = normal;
    this.std = std;
  }

  public Bandit(Random rand) {
    this.rand = rand;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Bandit that = (Bandit) o;

    return new EqualsBuilder()
        .append(normal, that.normal)
        .append(std, that.std)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(normal)
        .append(std)
        .toHashCode();
  }
}
