package dev.aisandbox.client.scenarios.twisty;

public class NotExistentMoveException extends Exception {

  public NotExistentMoveException(String message) {
    super(message);
  }

  public NotExistentMoveException(String message, Throwable cause) {
    super(message, cause);
  }
}
