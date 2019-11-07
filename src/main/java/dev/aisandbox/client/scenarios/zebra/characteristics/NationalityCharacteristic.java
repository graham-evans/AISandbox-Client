package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class NationalityCharacteristic extends GenericCharacteristic implements Characteristic {

    public NationalityCharacteristic() {
        super(
                new String[]{"German", "British", "Spanish", "Danish", "Norwegian", "Dutch", "French", "Chinese", "Greek", "American"},
                new String[]{"is #"},
                new String[]{"isn't #"}
        );
    }
}
