package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class CurtainColourCharacteristic implements Characteristic {

    String[] names = new String[]{"red", "blue", "green", "black", "white", "maroon", "olive", "aquamarine", "fuchia", "pink"};


    @Override
    public String getItem(int num) {
        return names[num];
    }

    @Override
    public String getDescription(int num) {
        return "has " + names[num] + " curtains";
    }

    @Override
    public String getNegativeDescription(int num) {
        return "doesn't have " + names[num] + " curtains";
    }
}