package dev.aisandbox.client.scenarios.parameters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BooleanParameterTest {
  @Test
  public void booleanSetTest() {
    BooleanParameter bp = new BooleanParameter("key", true);
    assertEquals("Starting value", true, bp.getValue());
  }

  @Test
  public void longParseTest() {
    BooleanParameter bp = new BooleanParameter("key", true);
    bp.setParsableValue("false");
    assertEquals("Parsed value", false, bp.getValue());
  }
}
