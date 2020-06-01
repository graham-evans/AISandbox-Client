package dev.aisandbox.client.agent;

import java.io.IOException;
import lombok.Getter;

/**
 * AgentException class.
 *
 * <p>Thrown when trying to talk to a target agent.
 */
public class AgentException extends IOException {

  @Getter private final String target;

  /**
   * Constructor for AgentException.
   *
   * @param message a {@link java.lang.String} object.
   */
  public AgentException(String target, String message) {
    super(message);
    this.target = target;
  }

  /**
   * Constructor for AgentException.
   *
   * @param message a {@link java.lang.String} object.
   * @param cause a {@link java.lang.Throwable} object.
   */
  public AgentException(String target, String message, Throwable cause) {
    super(message, cause);
    this.target = target;
  }
}
