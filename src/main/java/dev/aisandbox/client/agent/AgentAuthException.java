package dev.aisandbox.client.agent;

public class AgentAuthException extends AgentException {
  /**
   * Constructor for AgentAuthException.
   *
   * @param target The URL being called
   * @param message a {@link java.lang.String} object.
   */
  public AgentAuthException(String target, String message) {
    super(target, message);
  }
}
