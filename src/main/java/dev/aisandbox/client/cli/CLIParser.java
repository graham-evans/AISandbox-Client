package dev.aisandbox.client.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CLIParser {

  private static final Logger LOG = LoggerFactory.getLogger(CLIParser.class.getName());

  public static final String OPTION_DEBUG = "logfile";
  public static final String OPTION_LILITH = "lilith";
  public static final String OPTION_CONFIG = "config";
  public static final String OPTION_HEADLESS = "headless";

  /**
   * Generate and return the options object.
   *
   * <p>This discribes the different options available to the user.
   *
   * @return The Options object.
   */
  public static Options getOptions() {
    Options options = new Options();

    options.addOption(
        Option.builder(OPTION_CONFIG)
            .hasArg()
            .argName("file")
            .desc("Read from a configuration file")
            .build());
    options.addOption(OPTION_DEBUG, false, "Write debug log to file");
    options.addOption(OPTION_LILITH, false, "Connect to lilith log viewer on localhost");
    options.addOption(OPTION_HEADLESS, false, "Run the simulation without the GUI");
    return options;
  }
}
