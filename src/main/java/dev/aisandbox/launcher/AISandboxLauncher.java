package dev.aisandbox.launcher;

import dev.aisandbox.client.cli.CLIParser;
import javafx.application.Application;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

/**
 * Launcher class for AI Sandbox.
 *
 * <p>This will test for the "-headless" option and either call the CLI application launcher of the
 * FX version.
 *
 * @author gde
 * @version $Id: $Id
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
    // read command line arguments
    CommandLine cmd = CLIParser.parseOptions(args);
    // check for headless and launch the correct application (passing the args)
    if (cmd.hasOption(CLIParser.OPTION_HEADLESS)) {
      SpringApplication.run(AISandboxFX.class, args);
    } else {
      Application.launch(AISandboxFX.class, args);
    }
  }
}
