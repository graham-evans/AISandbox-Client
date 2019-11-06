package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class HolidayCharacteristic implements Characteristic {

    String[] names = new String[]{"Germany", "Spain", "Denmark", "Norway", "Sweden", "Belgium", "Poland", "Italy", "Portugal", "USA"};

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
        return "went to " + names[num] + " on holiday";
    }

    @Override
    public String getNegativeDescription(int num) {
        return "didn't go to " + names[num] + " on holiday";
    }
}
