package dev.aisandbox.launcher;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

/**
 * Launcher class for AI Sandbox.
 *
 * <p>This will test for the "-headless" option and either call the CLI application launcher of the
 * FX version.
 */
public class AISandboxLauncher {
  private static final Logger LOG = LoggerFactory.getLogger(AISandboxLauncher.class);

  /**
   * Main point of entry for both the CLI and FX versions of the app.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    LOG.info("Launching AISandbox");
    // test for the headless option
    boolean headless = false;
    for (String arg : args) {
      if ("-headless".equals(arg)) {
        headless = true;
      }
    }
    if (headless) {
      SpringApplication.run(AISandboxFX.class, args);
    } else {
      Application.launch(AISandboxFX.class, args);
    }
  }
}
