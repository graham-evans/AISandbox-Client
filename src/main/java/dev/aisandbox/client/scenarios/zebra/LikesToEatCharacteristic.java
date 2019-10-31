package dev.aisandbox.client.scenarios.zebra;

import org.springframework.stereotype.Component;

@Component
public class LikesToEatCharacteristic implements Characteristic {

    String[] names = new String[]{"apples", "limes", "oranges", "cake", "lamb", "plumbs", "jam", "peanuts", "bread", "mushrooms"};

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
        return "eats " + names[num];
    }

    @Override
    public String getNegativeDescription(int num) {
        return "doesn't eat " + names[num];
    }
}