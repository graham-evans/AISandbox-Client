package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class FrontDoorCharacteristic implements Characteristic {

    String[] names = new String[]{"red", "blue", "green", "black", "grey", "orange", "brown", "lime coloured", "silver", "plum"};

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
        return "has a " + names[num] + " front door";
    }

    @Override
    public String getNegativeDescription(int num) {
        return "doesn't have a " + names[num] + " front door";
    }

}
