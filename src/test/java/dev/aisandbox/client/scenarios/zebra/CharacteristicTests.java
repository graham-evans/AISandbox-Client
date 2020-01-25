package dev.aisandbox.client.scenarios.zebra;

import static org.junit.Assert.assertFalse;

import dev.aisandbox.client.scenarios.zebra.vo.Characteristic;
import dev.aisandbox.client.scenarios.zebra.vo.CharacteristicObject;
import dev.aisandbox.client.scenarios.zebra.vo.Template;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

public class CharacteristicTests {

  Random rand = new Random();

  @Test
  public void uniqueCharacteristicNameTest() {
    Template t = CharacteristicGenerator.createTemplate(rand, 11, 10);
    Set<String> names = new HashSet<>();
    for (Characteristic c : t.getCharacteristics()) {
      assertFalse(
          "Duplicate characteristic name'" + c.getName() + "'", names.contains(c.getName()));
      names.add(c.getName());
    }
  }

  @Test
  public void uniqueDescriptionTest() {
    // all descriptions (positive and negative) should be unique
    Set<String> names = new HashSet<>();
    // every description, from every characteristic, should be unique
    Template t = CharacteristicGenerator.createTemplate(rand, 11, 10);
    for (Characteristic c : t.getCharacteristics()) {
      for (CharacteristicObject o : c.getInstances()) {
        String d1 = o.getPositiveDescription();
        String d2 = o.getNegativeDescription();
        assertFalse("Duplicate description '" + d1 + "'", names.contains(d1));
        names.add(d1);
        assertFalse("Duplicate description '" + d2 + "'", names.contains(d2));
        names.add(d2);
      }
    }
  }

  @Test
  public void uniqueInstanceTest() {
    // the name of each characteristic object should be unique within that characteristic
    Template t = CharacteristicGenerator.createTemplate(rand, 11, 10);
    for (Characteristic c : t.getCharacteristics()) {
      Set<String> names = new HashSet<>();
      for (CharacteristicObject o : c.getInstances()) {
        assertFalse("Duplicate instance name '" + o.getName() + "'", names.contains(o.getName()));
        names.add(o.getName());
      }
    }
  }

  @Test
  public void uniqueIconTest() {
    // the name of each icon object should be unique
    Template t = CharacteristicGenerator.createTemplate(rand, 11, 10);
    Set<Integer> names = new HashSet<>();
    for (Characteristic c : t.getCharacteristics()) {
      for (CharacteristicObject o : c.getInstances()) {
        assertFalse("Duplicate icon name '" + o.getIcon() + "'", names.contains(o.getIcon()));
        names.add(o.getIcon());
      }
    }
  }
}
