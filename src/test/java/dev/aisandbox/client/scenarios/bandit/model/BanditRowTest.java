package dev.aisandbox.client.scenarios.bandit.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import org.junit.Test;

public class BanditRowTest {

  @Test
  public void testDuplicateBandits() {
    Random r = new Random();
    BanditSession row =
        new BanditSession(r, 10, BanditNormalEnumeration.NORMAL_0_1, BanditStdEnumeration.ONE);
    assertFalse("Initalised with duplicate bandits", row.isInvalidBanditStart());
    // duplicate two bandits
    row.getBandits().get(3).setMean(4.0);
    row.getBandits().get(7).setMean(4.0);
    // this should give an error
    assertTrue("Duplicates not being detected", row.isInvalidBanditStart());
  }
}
