package dev.aisandbox.client.fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoggingChangeListener class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class LoggingChangeListener implements ChangeListener<Number> {

  private static final Logger LOG = LoggerFactory.getLogger(LoggingChangeListener.class.getName());

  private final String name;

  /**
   * Constructor for LoggingChangeListener.
   *
   * @param name a {@link java.lang.String} object.
   */
  public LoggingChangeListener(String name) {
    this.name = name;
  }

  /** {@inheritDoc} */
  @Override
  public void changed(
      ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    LOG.info("{} => {}", name, newValue);
  }
}
