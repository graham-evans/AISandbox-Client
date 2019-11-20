package dev.aisandbox.client.scenarios.zebra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CharacteristicTests {
/*
    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private List<Characteristic> characteristicList;

    @Test
    public void characteristicAvailabilityTest() {
        // check autowiring workd
        assertNotNull("Autowiring fail", characteristicList);
        // check count
        assertEquals("count mismatch", ZebraPuzzle.PUZZLE_CHARACTERISTICS, characteristicList.size());
    }

    @Test
    public void characteristicSizeTest() {
        // every characteristic should have ZebraPuzzle.PUZZLE_WIDTH items
        for (Characteristic c : characteristicList) {
            // item name
            assertNotNull(c.getClass().getName() + " item count", c.getItem(ZebraPuzzle.PUZZLE_HOUSES - 1));
            // positive description
            assertNotNull(c.getClass().getName() + " positive description count", c.getDescription(ZebraPuzzle.PUZZLE_HOUSES - 1));
            // negative description
            assertNotNull(c.getClass().getName() + " negative description count", c.getNegativeDescription(ZebraPuzzle.PUZZLE_HOUSES - 1));
        }
    }

    @Test
    public void uniqueDescriptionTest() {
        Set<String> names = new HashSet<>();
        // every description, from every characteristic, should be unique
        for (Characteristic c : characteristicList) {
            for (int i = 0; i < ZebraPuzzle.PUZZLE_HOUSES; i++) {
                String description = c.getDescription(i);
                assertFalse("Duplicate description " + c.getClass().getName() + " " + description, names.contains(description));
                names.add(description);
                String negativeDescription = c.getNegativeDescription(i);
                assertFalse("Duplicate negative description " + c.getClass().getName() + " " + negativeDescription, names.contains(negativeDescription));
                names.add(negativeDescription);
            }
        }
    }
*/
}
