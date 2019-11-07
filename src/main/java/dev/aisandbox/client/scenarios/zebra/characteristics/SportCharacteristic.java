package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class SportCharacteristic extends GenericCharacteristic implements Characteristic {

    public SportCharacteristic() {
        super(
                new String[]{"football", "rugby", "golf", "tennis", "athletics", "basketball", "baseball", "badminton", "volleyball", "cycling"},
                new String[]{"likes to watch #"},
                new String[]{"doesn't watch #"}
        );
    }

}