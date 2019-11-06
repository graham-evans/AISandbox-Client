package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class DrivesCharacteristic implements Characteristic {

    String[] names = new String[]{"Mazda", "Vauxhall", "Volvo", "Audi", "BMW", "Citroen", "Fiat", "Ford", "Honda", "Land Rover"};

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
        return "drives a " + names[num];
    }

    @Override
    public String getNegativeDescription(int num) {
        return "doesn't drive a " + names[num];
    }
}
