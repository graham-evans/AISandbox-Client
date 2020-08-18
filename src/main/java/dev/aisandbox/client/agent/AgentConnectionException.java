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
   * @param target The URL being called
   * @param message a {@link java.lang.String} object.
   */
  public AgentConnectionException(String target, String message) {
    super(target, message);
  }
}
