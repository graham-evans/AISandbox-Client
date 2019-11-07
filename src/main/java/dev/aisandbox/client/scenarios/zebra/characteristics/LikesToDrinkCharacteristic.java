package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class LikesToDrinkCharacteristic extends GenericCharacteristic implements Characteristic {

    public LikesToDrinkCharacteristic() {
        super(
                new String[]{"beer", "tea", "milk", "coffee", "water", "lemonade", "red wine", "white wine", "vodka", "gin"},
                new String[]{"drinks #"},
                new String[]{"doesn't drink #"}
        );
    }

}
