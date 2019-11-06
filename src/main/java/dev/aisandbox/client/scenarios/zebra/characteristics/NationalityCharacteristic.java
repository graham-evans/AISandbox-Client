package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class NationalityCharacteristic implements Characteristic {

    String[] names = new String[]{"German", "British", "Spanish", "Danish", "Norwegian", "Dutch", "French", "Chinese", "Greek", "American"};

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
        return "who is " + names[num];
    }

    @Override
    public String getNegativeDescription(int num) {
        return "isn't " + names[num];
    }
}
