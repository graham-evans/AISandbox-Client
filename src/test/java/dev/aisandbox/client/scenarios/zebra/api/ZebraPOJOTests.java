package dev.aisandbox.client.scenarios.zebra.api;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

public class ZebraPOJOTests {

  @Test
  public void HouseTest() {
    assertPojoMethodsFor(House.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
  }

  @Test
  public void HouseCharacteristicsTest() {
    assertPojoMethodsFor(HouseCharacteristics.class)
        .testing(Method.GETTER, Method.SETTER)
        .areWellImplemented();
  }

  @Test
  public void SolutionTest() {
    assertPojoMethodsFor(Solution.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
  }

  @Test
  public void ZebraRequestTest() {
    assertPojoMethodsFor(ZebraRequest.class)
        .testing(Method.GETTER, Method.SETTER)
        .areWellImplemented();
  }

  @Test
  public void ZebraRequestCharacteristics() {
    assertPojoMethodsFor(ZebraRequestCharacteristics.class)
        .testing(Method.GETTER, Method.SETTER)
        .areWellImplemented();
  }

  @Test
  public void ZebraRequestEntriesTest() {
    assertPojoMethodsFor(ZebraRequestEntries.class)
        .testing(Method.GETTER, Method.SETTER)
        .areWellImplemented();
  }

  @Test
  public void ZebraRequestHistoryTest() {
    assertPojoMethodsFor(ZebraRequestHistory.class)
        .testing(Method.GETTER, Method.SETTER)
        .areWellImplemented();
  }

  @Test
  public void ZebraResponseTest() {
    assertPojoMethodsFor(ZebraResponse.class)
        .testing(Method.GETTER, Method.SETTER)
        .areWellImplemented();
  }
}
