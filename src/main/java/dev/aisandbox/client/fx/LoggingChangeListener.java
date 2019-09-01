package dev.aisandbox.client.fx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.logging.Logger;

public class LoggingChangeListener implements ChangeListener<Number> {

    private Logger LOG = Logger.getLogger(LoggingChangeListener.class.getName());

    private final String name;

    public LoggingChangeListener(String name) {
        this.name = name;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        LOG.info(name + " => " + newValue.toString());
    }
}
