package dev.aisandbox.client.agent;

import java.io.IOException;

public class AgentException extends IOException {

  public AgentException(String message) {
    super(message);
  }

  public AgentException(String message, Throwable cause) {
    super(message, cause);
  }
}
