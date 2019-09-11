package dev.aisandbox.client.fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingChangeListener implements ChangeListener<Number> {

    private static final Logger LOG = Logger.getLogger(LoggingChangeListener.class.getName());

    private final String name;

    public LoggingChangeListener(String name) {
        this.name = name;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        LOG.log(Level.INFO, "{0} => {1}", new Object[]{name, newValue});
    }
}
