package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class FrontDoorCharacteristic extends GenericCharacteristic implements Characteristic {

    public FrontDoorCharacteristic() {
        super(new String[]{"red", "blue", "green", "black", "grey", "orange", "brown", "lime coloured", "silver", "plum"},
                new String[]{"has a # front door"},
                new String[]{"doens't have a # front door"});
    }
}
