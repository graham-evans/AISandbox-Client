package dev.aisandbox.client.scenarios.zebra.characteristics;

import dev.aisandbox.client.scenarios.zebra.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class FlowersCharacteristic extends GenericCharacteristic implements Characteristic {

    public FlowersCharacteristic() {
        super(new String[]{"gerberas", "daisys", "geraniums", "crysanthemums", "lilies", "roses", "daffodils", "alastromeras", "cornflowers", "orchids"},
                new String[]{"likes #"},
                new String[]{"doesn't like #"});
    }
}
