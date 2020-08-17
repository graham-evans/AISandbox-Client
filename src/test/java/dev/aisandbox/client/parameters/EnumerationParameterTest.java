package dev.aisandbox.client.parameters;

import static org.junit.Assert.assertEquals;

import dev.aisandbox.client.scenarios.bandit.model.BanditNormalEnumeration;
import org.junit.Test;

public class EnumerationParameterTest {

  @Test
  public void setupTest() {
    EnumerationParameter<BanditNormalEnumeration> bp =
        new EnumerationParameter<>("paramKey", BanditNormalEnumeration.NORMAL_0_1);
    assertEquals("Name", "paramKey", bp.getParameterKey());
  }

  @Test
  public void ParseTest1() throws ParameterParseException {
    EnumerationParameter<BanditNormalEnumeration> bp =
        new EnumerationParameter<>("paramKey", BanditNormalEnumeration.NORMAL_0_1);
    bp.setParsableValue("UNIFORM_1_1");
    assertEquals("Value not parsed", BanditNormalEnumeration.UNIFORM_1_1, bp.getValue());
  }

  @Test(expected = ParameterParseException.class)
  public void ParseTest2() throws ParameterParseException {
    EnumerationParameter<BanditNormalEnumeration> bp =
        new EnumerationParameter<>("paramKey", BanditNormalEnumeration.NORMAL_0_1);
    bp.setParsableValue("xxxx");
  }
}
