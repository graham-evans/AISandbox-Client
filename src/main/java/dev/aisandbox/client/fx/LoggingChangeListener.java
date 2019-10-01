package dev.aisandbox.client.fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>LoggingChangeListener class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
public class LoggingChangeListener implements ChangeListener<Number> {

    private static final Logger LOG = Logger.getLogger(LoggingChangeListener.class.getName());

    private final String name;

    /**
     * <p>Constructor for LoggingChangeListener.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public LoggingChangeListener(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        LOG.log(Level.INFO, "{0} => {1}", new Object[]{name, newValue});
    }
}
