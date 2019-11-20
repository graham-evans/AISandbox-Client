package dev.aisandbox.client.scenarios.zebra;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.aisandbox.client.scenarios.zebra.vo.Characteristic;
import dev.aisandbox.client.scenarios.zebra.vo.Template;

import java.util.Random;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneratorTest {

    private static final Logger LOG = Logger.getLogger(GeneratorTest.class.getName());

    @Test
    public void generateTemplateTest() {
        Template t = CharacteristicGenerator.createTemplate(10, 10);
        assertNotNull("Template=null",t);
        assertEquals("Template characteristic count",10,t.getCharacteristics().size());
        for (Characteristic c : t.getCharacteristics()) {
            assertEquals("Characteristic length for "+c.getName(),10,c.getInstances().size());
        }
    }

    @Test
    public void generateSmallTemplateTest() {
        Template t = CharacteristicGenerator.createTemplate(8, 4);
        assertNotNull("Template=null",t);
        assertEquals("Template characteristic count",8,t.getCharacteristics().size());
        for (Characteristic c : t.getCharacteristics()) {
            assertEquals("Characteristic length for "+c.getName(),4,c.getInstances().size());
        }
    }
}
