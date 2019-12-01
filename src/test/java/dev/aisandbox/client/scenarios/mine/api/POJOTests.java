package dev.aisandbox.client.scenarios.mine.api;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class POJOTests {
    @Test
    public void PositionTest() {
        assertPojoMethodsFor(LastMove.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void ConfigTest() {
        assertPojoMethodsFor(MineHunterResponse.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void HistoryTest() {
        assertPojoMethodsFor(MineHunterRequest.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void MazeRequest() {
        assertPojoMethodsFor(Move.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }
}
