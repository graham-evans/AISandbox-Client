package dev.aisandbox.client;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class OutputFormatTest {

    @Test
    public void testUniqueName() {
        Set<String> names = new HashSet<>();
        for (OutputFormat t : OutputFormat.values()) {
            assertNotNull("null toString for " + t.name(), t.toString());
            assertFalse("Output type " + t.name() + " has duplicate toString", names.contains(t.toString()));
            names.add(t.toString());
        }
    }

}
