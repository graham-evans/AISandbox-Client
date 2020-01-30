package dev.aisandbox.client.cli;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import dev.aisandbox.client.AISandboxClient;
import javafx.scene.web.HTMLEditorSkin.Command;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
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

  public static CommandLine parseOptions(String[] args) {
    Options options = getOptions();
    CommandLine line = null;
    try {
      CommandLineParser parser = new DefaultParser();
      line = parser.parse(options, args);
    } catch (ParseException e) {
      LOG.warn("Error parsing command line arguments");
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("java -jar AISandbox_<version>.jar", options);
      System.exit(-1);
    }
    return line;
  }

  /** Enable the debug logfile. */
  public static void enableDegug() {
    LOG.warn("Writing debug to file.");
    // Get the logback context
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    try {
      JoranConfigurator configurator = new JoranConfigurator();
      configurator.setContext(context);
      // Dont call reset - we want to add this configuration to the existing one
      configurator.doConfigure(AISandboxClient.class.getResourceAsStream("/logback-debug.xml"));
    } catch (JoranException je) {
      // StatusPrinter will handle this
    }
    LOG.info("Enabled logfile");
  }

  /** Enable logging to the Lilith logback app */
  public static void enableLilith() {
    LOG.warn("Enabling lilith logging.");
    // Get the logback context
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    try {
      JoranConfigurator configurator = new JoranConfigurator();
      configurator.setContext(context);
      // Dont call reset - we want to add this configuration to the existing one
      configurator.doConfigure(AISandboxClient.class.getResourceAsStream("/logback-lilith.xml"));
    } catch (JoranException je) {
      // StatusPrinter will handle this
    }
    LOG.info("Enabled lilith logging");
  }
}
