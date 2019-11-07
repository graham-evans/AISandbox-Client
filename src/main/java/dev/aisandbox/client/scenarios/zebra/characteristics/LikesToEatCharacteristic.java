package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class LikesToEatCharacteristic extends GenericCharacteristic implements Characteristic {

    public LikesToEatCharacteristic() {
        super(
                new String[]{"apples", "limes", "oranges", "cake", "lamb", "plumbs", "jam", "peanuts", "bread", "mushrooms"},
                new String[]{"eats #"},
                new String[]{"doesn't eat #"}
        );
    }
}