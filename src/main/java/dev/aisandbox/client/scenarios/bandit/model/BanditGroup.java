package dev.aisandbox.client.scenarios.bandit.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.Getter;
import lombok.NonNull;

public class BanditGroup {

  private final int banditCount;
  private final Random rand;

  @Getter List<Bandit> bandits = new ArrayList<>();

  public BanditGroup(@NonNull Random rand, int banditCount) {
    this.rand = rand;
    this.banditCount = banditCount;
    for (int i = 0; i < banditCount; i++) {
      bandits.add(new Bandit(rand));
    }
    // initialise bandits
    while (isInvalidBanditStart()) {
      // randomise each bandit
      for (Bandit b : bandits) {
        // use normal dist
        b.setNormal(rand.nextGaussian());
        b.setStd(1);
      }
    }
  }

  /**
   * Check if the bandits are in an invalid start state.
   *
   * <p>This occurs if two bandits have exactly the same average and std, and can be detected when
   * two bandits have the same hashcode.
   * @return
   */
  protected boolean isInvalidBanditStart() {
    Set<Integer> hashes = new HashSet<>();
    for (Bandit b : bandits) {
      if (hashes.contains(b.hashCode())) {
        return true;
      }
      hashes.add(b.hashCode());
    }
    return false;
  }



}
