package dev.aisandbox.client.parameters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LongParameterTest {

  @Test
  public void longSetTest() {
    LongParameter lp = new LongParameter("key", 123, "Test parameter", "description");
    assertEquals("Starting value", 123, lp.getValue());
  }

  @Test
  public void longParseTest() throws ParameterParseException {
    LongParameter lp = new LongParameter("key", 123, "Test parameter", "description");
    lp.setParsableValue("54321");
    assertEquals("Parsed value", 54321, lp.getValue());
  }
}
