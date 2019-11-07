package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class HolidayCharacteristic extends GenericCharacteristic implements Characteristic {

    public HolidayCharacteristic() {
        super(
                new String[]{"Germany", "Spain", "Denmark", "Norway", "Sweden", "Belgium", "Poland", "Italy", "Portugal", "USA"},
                new String[]{"went to # on holiday"},
                new String[]{"didn't go to # on holiday"}
        );
    }

}
