package dev.aisandbox.client.scenarios.zebra.api;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class ZebraPOJOTests {

    @Test
    public void HouseAnswerTest() {
        assertPojoMethodsFor(HouseAnswer.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void PropertyDefinitionTest() {
        assertPojoMethodsFor(PropertyDefinition.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void ZebraRequestTest() {
        assertPojoMethodsFor(ZebraRequest.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void ZebraRequestHistoryTest() {
        assertPojoMethodsFor(ZebraRequestHistory.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void ZebraResponseTest() {
        assertPojoMethodsFor(ZebraResponse.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }
}
