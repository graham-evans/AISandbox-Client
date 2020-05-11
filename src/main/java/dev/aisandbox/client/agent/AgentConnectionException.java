package dev.aisandbox.client.agent;

/**
 * AgentConnectionException class.
 *
 * <p>Thrown when the client cannot connect to the target URL.
 */
public class AgentConnectionException extends AgentException {

  /**
   * Constructor for AgentConnectionException.
   *
   * @param message a {@link java.lang.String} object.
   */
  public AgentConnectionException(String message) {
    super(message);
  }
}
