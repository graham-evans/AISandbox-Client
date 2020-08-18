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

  public BanditSession(
      @NonNull Random rand,
      int banditCount,
      BanditNormalEnumeration normal,
      BanditStdEnumeration std) {
    this.rand = rand;
    this.banditCount = banditCount;
    // generate bandits
    for (int i = 0; i < banditCount; i++) {
      double n;
      switch (normal) {
        case NORMAL_0_5:
          n = rand.nextGaussian() * 5.0;
          break;
        case UNIFORM_1_1:
          n = rand.nextDouble() * 2.0 - 1.0;
          break;
        case UNIFORM_0_5:
          n = rand.nextDouble() * 5.0;
          break;
        default: // NORMAL_0_1:
          n = rand.nextGaussian();
      }
      double s;
      switch (std) {
        case FIVE:
          s = 5.0;
          break;
        case TENTH:
          s = 0.1;
          break;
        default: // 1
          s = 1.0;
      }
      bandits.add(new Bandit(rand, n, s));
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
   * @return is this an invalid starting state
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
   * @param number the number of the selected bandit
   * @return true if the selected bandit has the highest mean value
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

  /** Update the bandits by moving each mean N(0,0.1) */
  public void updateRandom() {
    for (Bandit b : bandits) {
      b.setMean(b.getMean() + rand.nextGaussian() * 0.001);
    }
  }

  /**
   * Update the bandits by moving the chosen bandit by -0.001
   *
   * @param chosen the index of the chosen bandit
   */
  public void updateFade(int chosen) {
    Bandit target = bandits.get(chosen);
    target.setMean(target.getMean() - 0.001);
  }

  /**
   * Update the bandits by moving the chosed bandit by -0.001 and all others by +0.001/k
   *
   * @param chosen the index of the chosen bandit
   */
  public void updateEqualise(int chosen) {
    double reward = 0.001 / bandits.size();
    for (int i = 0; i < bandits.size(); i++) {
      Bandit b = bandits.get(i);
      if (i == chosen) {
        b.setMean(b.getMean() - 0.001);
      } else {
        b.setMean(b.getMean() + reward);
      }
    }
  }
}
