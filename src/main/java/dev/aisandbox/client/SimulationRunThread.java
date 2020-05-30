package dev.aisandbox.client;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimulationRunThread extends Thread {

  private final ApplicationModel model;

  private long stepcount;

  @Setter private boolean stopped = false;

  public SimulationRunThread(ApplicationModel model, long stepcount) {
    this.model = model;
    this.stepcount = stepcount;
  }

  public SimulationRunThread(ApplicationModel model) {
    this.model = model;
    this.stepcount = -1;
  }

  @Override
  public void run() {
    try {
      while ((stepcount != 0) && !stopped) {
        log.info("Advancing simulation");
        model.advanceRuntime();
        if (stepcount > 0) {
          stepcount--;
        }
      }
    } catch (Exception e) {
      log.warn("Exception while running simulation", e);
    }
    // Tell the UI that the run has finished
    model.getGameRunController().resetStartButton();
  }
}
