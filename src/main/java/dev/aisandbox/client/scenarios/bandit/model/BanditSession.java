package dev.aisandbox.client.scenarios.bandit.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;

public class BanditSession {

  private final int banditCount;
  private final Random rand;

  @Getter List<Bandit> bandits = new ArrayList<>();
  @Getter String sessionID = UUID.randomUUID().toString();
  @Getter double score = 0.0;

  public BanditSession(@NonNull Random rand, int banditCount) {
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
        b.setMean(rand.nextGaussian());
        b.setStd(1);
      }
    }
  }

  /**
   * Check if the bandits are in an invalid start state.
   *
   * <p>This occurs if two bandits have exactly the same average and std, and can be detected when
   * two bandits have the same hashcode.
   *
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

  /**
   * Query if the selected bandit is currently the 'best'.
   *
   * <p>Defined as whether there is a bandit with a higher mean than the one selected.
   *
   * @param number
   * @return
   */
  public boolean isBestMean(int number) {
    double mean = bandits.get(number).getMean();
    for (int i = 0; i < bandits.size(); i++) {
      if ((i != number) && (bandits.get(i).getMean() > mean)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Pull the arm on the requested bandit.
   *
   * @param number the number of bandit to select (numbered 0 to n-1).
   * @return The reward obtained.
   */
  public double activateBandit(int number) {
    // pull the bandits arm
    double result = bandits.get(number).pull();
    // record the score
    score += result;
    // return the result
    return result;
  }
}
