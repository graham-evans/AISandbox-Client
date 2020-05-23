package dev.aisandbox.client.scenarios.parameters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OptionParameterTest {

  @Test
  public void parseNumberTest() {
    OptionParameter e =
        new OptionParameter("key", new String[] {"Option 0", "Option 1", "Option 2"});
    e.setParsableValue("1");
    assertEquals("Number Value", 1, e.getOptionIndex());
    assertEquals("Test Value", "Option 1", e.getOptionString());
  }

  @Test
  public void parseTextTest() {
    OptionParameter e =
        new OptionParameter("key", new String[] {"Option 0", "Option 1", "Option 2"});
    e.setParsableValue("Option 1");
    assertEquals("Number Value", 1, e.getOptionIndex());
    assertEquals("Test Value", "Option 1", e.getOptionString());
  }
}
