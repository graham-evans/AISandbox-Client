package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class PetCharacteristic implements Characteristic {

    String[] names = new String[]{"cat", "dog", "fish", "rabbit", "mice", "ferrets", "frog", "goat", "horse", "iguana"};

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
        return "has a " + names[num];
    }

    @Override
    public String getNegativeDescription(int num) {
        return "doesn't have a " + names[num];
    }
}
