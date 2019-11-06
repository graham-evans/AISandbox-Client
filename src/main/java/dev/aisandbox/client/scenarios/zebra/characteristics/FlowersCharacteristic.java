package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class FlowersCharacteristic implements Characteristic {

    String[] names = new String[]{"gerberas", "daisys", "geraniums", "crysanthemums", "lilies", "roses", "daffodils", "alastromeras", "cornflowers", "orchids"};

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
        return "likes " + names[num];
    }

    @Override
    public String getNegativeDescription(int num) {
        return "doesn't like " + names[num];
    }
}
