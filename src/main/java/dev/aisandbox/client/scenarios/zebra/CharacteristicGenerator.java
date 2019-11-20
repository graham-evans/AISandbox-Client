package dev.aisandbox.client.scenarios.zebra;

public class CharacteristicGenerator {
    public static Template createTemplate(int characteristics,int houses) {
        Template t = new Template();
        // populate template 
        Characteristic c1 = new Characteristic();
        c1.setName("Curtain Colour");
        CharacteristicObject o1 = new CharacteristicObject();
        o1.setName("Blue");
        o1.setPositiveDescription("has blue curtains");
        o1.setNegativeDescription("doesn't have blue curtains");
        o1.setAlternativeNegativeDescription(new String[] {"has blue curtains","curtains are blue"});
        o1.setAlternativeNegativeDescription(new String[] {"doesn't have blue curtains","does not have blue curtains"});
        c1.getInstances().add(o1);
        t.getCharacteristics().add(c1);
        return t;
    }
}