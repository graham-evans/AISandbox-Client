package dev.aisandbox.client.scenarios.twisty;

import static org.junit.Assert.assertFalse;

import dev.aisandbox.client.scenarios.twisty.tpmodel.ColourEnum;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class ColourEnumTest {

  @Test
  public void colourDifferenceTest() {
    // make sure all available colours have different awt.color's and characters
    Set<Character> codes = new HashSet<>();
    Set<Integer> rgb = new HashSet<>();
    for (ColourEnum e : ColourEnum.values()) {
      // check codes
      assertFalse("Duplicate colour code " + e.getCharacter(), codes.contains(e.getCharacter()));
      codes.add(e.getCharacter());
      // check AWT
      assertFalse("Duplicate RGB with " + e.getHex(), rgb.contains(e.getAwtColour().getRGB()));
      rgb.add(e.getAwtColour().getRGB());
    }
  }
}
