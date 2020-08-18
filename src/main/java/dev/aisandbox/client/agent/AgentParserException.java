package dev.aisandbox.client.agent;

import lombok.Getter;

/**
 * AgentParserException class.
 *
 * <p>Thrown when the payload from the user is not parsable.
 */
public class AgentParserException extends AgentException {

  @Getter private final int responseCode;
  @Getter private final String response;

  /**
   * Exception thrown when the agent tries to decode the JSON / XML.
   *
   * @param target The URL being called
   * @param message User readable message
   */
  public AgentParserException(String target, String message) {
    super(target, message);
    response = null;
    responseCode = -1;
  }

  /**
   * More detailed constructor.
   *
   * @param target The URL being called
   * @param message Description of the error
   * @param code HTTP code from the server
   * @param content The payload that was sent
   */
  public AgentParserException(String target, String message, int code, String content) {
    super(target, message);
    responseCode = code;
    response = content;
  }
}
