package dev.aisandbox.client.scenarios.maze;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * <p>MazeScenarioBeanInfo class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
public class MazeScenarioBeanInfo extends SimpleBeanInfo {

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor flc = new PropertyDescriptor("scenarioSalt", MazeScenario.class);
            flc.setDisplayName("Starting Salt (0=random)");
            flc.setName("A");
            PropertyDescriptor fic = new PropertyDescriptor("mazeType", MazeScenario.class);
            fic.setDisplayName("Maze Type");
            fic.setName("B");
            PropertyDescriptor[] list = { flc, fic};
            return list;
        } catch (IntrospectionException iexErr) {
            throw new Error(iexErr.toString());
        }
    }
}
