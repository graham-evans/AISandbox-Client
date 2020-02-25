package dev.aisandbox.client.scenarios.twisty;

import lombok.Data;

@Data
public class TwistyThread extends Thread {

  private boolean running = false;
}
