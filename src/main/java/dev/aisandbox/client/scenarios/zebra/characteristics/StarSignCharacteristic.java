package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class StarSignCharacteristic implements Characteristic {

    String[] names = new String[]{"pisces", "aries", "taurus", "gemini", "cancer", "leo", "vergo", "libra", "scorpio", "sagittarius"};

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
        return "has " + names[num] + " as their star sign";
    }

    @Override
    public String getNegativeDescription(int num) {
        return "hasn't got " + names[num] + " as their star sign";
    }
}
