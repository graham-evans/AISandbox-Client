package dev.aisandbox.client.parameters;

import static org.junit.Assert.assertEquals;

import dev.aisandbox.client.scenarios.bandit.model.BanditCountEnumeration;
import org.junit.Test;

public class NumberEnumerationParameterTest {
  @Test
  public void numberParseTest() throws ParameterParseException {
    NumberEnumerationParameter<BanditCountEnumeration> bce =
        new NumberEnumerationParameter<>(
            "bandit.count", BanditCountEnumeration.TEN, "Test Parameter", "description");
    bce.setParsableValue("50");
    assertEquals("Not parsed", 50, bce.getValue().getNumber());
  }
}
