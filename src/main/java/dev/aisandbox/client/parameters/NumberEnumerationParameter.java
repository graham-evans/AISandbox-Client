package dev.aisandbox.client.parameters;

import lombok.extern.slf4j.Slf4j;

/**
 * A parameter that exposes an enumerated set of numbers.
 *
 * <p>This extends <i></i>EnumerationParameter</i> and behaves in a similar manner, except that CLI
 * parsing is done on the number not the name.
 *
 * @param <T> The enumeration to expose
 */
@Slf4j
public class NumberEnumerationParameter<T extends Enum<T>> extends EnumerationParameter<T> {

  /**
   * Constructor for a choice of enumerated numbers.
   *
   * @param key the name of the parameter
   * @param startChoice the <i>Enum</i> to choose a value from
   * @param name The label to show in the UI
   * @param tooltip A description
   */
  public NumberEnumerationParameter(String key, T startChoice, String name, String tooltip) {
    super(key, startChoice, name, tooltip);
  }

  @Override
  public void setParsableValue(String val) throws ParameterParseException {

    log.info("Setting {} to {}", this.parameterKey, val);
    boolean found = false;

    for (T t : value.getDeclaringClass().getEnumConstants()) {
      log.info("Testing {} ", t);
      if (t.toString().equalsIgnoreCase(val)) {
        found = true;
        value = t;
      }
    }
    if (!found) {
      throw new ParameterParseException("Can't parse " + val + " to enumerated value");
    }
  }
}
