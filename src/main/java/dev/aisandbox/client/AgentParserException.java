package dev.aisandbox.client;

import lombok.Getter;

public class AgentParserException extends AgentException {

  @Getter private final int responseCode;
  @Getter private final String response;

  public AgentParserException(String message) {
    super(message);
    response = null;
    responseCode = -1;
  }

  public AgentParserException(String message, int code, String content) {
    super(message);
    responseCode = code;
    response = content;
  }
}
