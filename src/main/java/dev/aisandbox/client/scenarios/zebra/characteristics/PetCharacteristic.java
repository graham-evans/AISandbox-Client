package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class PetCharacteristic extends GenericCharacteristic implements Characteristic {

    public PetCharacteristic() {
        super(
                new String[]{"cat", "dog", "fish", "rabbit", "mice", "ferrets", "frog", "goat", "horse", "iguana"},
                new String[]{"has a #"},
                new String[]{"doesn't have a #"}
        );
    }
}
