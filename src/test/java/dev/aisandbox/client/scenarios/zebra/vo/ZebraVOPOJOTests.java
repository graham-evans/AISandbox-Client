package dev.aisandbox.client.scenarios.zebra.vo;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

public class ZebraVOPOJOTests {

  @Test
  public void CharacteristicTest() {
    assertPojoMethodsFor(Characteristic.class)
        .testing(Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE)
        .areWellImplemented();
  }

  @Test
  public void CharacteristicObjectTest() {
    assertPojoMethodsFor(CharacteristicObject.class)
        .testing(Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE)
        .areWellImplemented();
  }
}
