package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class StarSignCharacteristic extends GenericCharacteristic implements Characteristic {

    public StarSignCharacteristic() {
        super(
                new String[]{"pisces", "aries", "taurus", "gemini", "cancer", "leo", "vergo", "libra", "scorpio", "sagittarius"},
                new String[]{"has # as their star sign"},
                new String[]{"hasn't got # as their star sign"}
        );
    }
}
