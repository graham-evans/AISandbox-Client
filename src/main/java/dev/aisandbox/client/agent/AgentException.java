package dev.aisandbox.client.agent;

import java.io.IOException;

/**
 * AgentException class.
 *
 * <p>Thrown when trying to talk to a target agent.
 */
public class AgentException extends IOException {

  /**
   * Constructor for AgentException.
   *
   * @param message a {@link java.lang.String} object.
   */
  public AgentException(String message) {
    super(message);
  }

  /**
   * Constructor for AgentException.
   *
   * @param message a {@link java.lang.String} object.
   * @param cause a {@link java.lang.Throwable} object.
   */
  public AgentException(String message, Throwable cause) {
    super(message, cause);
  }
}
