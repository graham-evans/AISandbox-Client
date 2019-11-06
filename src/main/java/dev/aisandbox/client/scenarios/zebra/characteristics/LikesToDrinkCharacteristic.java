package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class LikesToDrinkCharacteristic implements Characteristic {

    String[] names = new String[]{"beer", "tea", "milk", "coffee", "water", "lemonade", "red wine", "white wine", "vodka", "gin"};

    @Override
    public String getIcon(int num) {
        return names[num] + ".png";
    }

    @Override
    public String getItem(int num) {
        return names[num];
    }

    @Override
    public String getDescription(int num) {
        return "drinks " + names[num];
    }

    @Override
    public String getNegativeDescription(int num) {
        return "doesn't drink " + names[num];
    }
}
