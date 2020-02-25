package dev.aisandbox.client.fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.extern.slf4j.Slf4j;

/**
 * LoggingChangeListener class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
public class LoggingChangeListener implements ChangeListener<Number> {

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
    log.info("{} => {}", name, newValue);
  }
}
