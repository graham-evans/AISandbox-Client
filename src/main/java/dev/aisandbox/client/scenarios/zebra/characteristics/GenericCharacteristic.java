package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GenericCharacteristic implements Characteristic {

    private final String[] names;
    private final String[] positiveFormats;
    private final String[] negativeFormats;

    @Override
    public String getItem(int num) {
        return names[num];
    }

    @Override
    public String getDescription(int num) {
        return positiveFormats[0].replaceAll("#", names[num]);
    }

    @Override
    public String getNegativeDescription(int num) {
        return negativeFormats[0].replaceAll("#", names[num]);
    }
}
