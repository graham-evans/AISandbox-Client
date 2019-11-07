package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class DrivesCharacteristic extends GenericCharacteristic implements Characteristic {

    public DrivesCharacteristic() {
        super(new String[]{"Mazda", "Vauxhall", "Volvo", "Audi", "BMW", "Citroen", "Fiat", "Ford", "Honda", "Land Rover"},
                new String[]{"drives a #"},
                new String[]{"doesn't drive a #"});
    }
}
