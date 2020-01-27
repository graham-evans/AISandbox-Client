package dev.aisandbox.client;

import lombok.Getter;

public class AgentParserException extends AgentException {

  @Getter private final int responseCode;
  @Getter private final String response;

  /**
   * Exception thrown when the agent tries to decode the JSON / XML.
   * @param message User readable message
   */
  public AgentParserException(String message) {
    super(message);
    response = null;
    responseCode = -1;
  }

  /**
   * More detailed constructor.
   * @param message Description of the error
   * @param code HTTP code from the server
   * @param content The payload that was sent
   */
  public AgentParserException(String message, int code, String content) {
    super(message);
    responseCode = code;
    response = content;
  }
}
