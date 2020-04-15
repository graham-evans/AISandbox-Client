package dev.aisandbox.client.scenarios.twisty;

/**
 * NotExistentMoveException class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class NotExistentMoveException extends Exception {

  /**
   * Constructor for NotExistentMoveException.
   *
   * @param message a {@link java.lang.String} object.
   */
  public NotExistentMoveException(String message) {
    super(message);
  }

  /**
   * Constructor for NotExistentMoveException.
   *
   * @param message a {@link java.lang.String} object.
   * @param cause a {@link java.lang.Throwable} object.
   */
  public NotExistentMoveException(String message, Throwable cause) {
    super(message, cause);
  }
}
