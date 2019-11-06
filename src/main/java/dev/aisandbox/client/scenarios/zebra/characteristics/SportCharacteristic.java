package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class SportCharacteristic implements Characteristic {

    String[] names = new String[]{"football", "rugby", "golf", "tennis", "athletics", "basketball", "baseball", "badminton", "volleyball", "cycling"};

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
        return "likes to watch " + names[num];
    }

    @Override
    public String getNegativeDescription(int num) {
        return "doesn't watch " + names[num];
    }
}