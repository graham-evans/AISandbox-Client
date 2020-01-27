package dev.aisandbox.client.cli;

import dev.aisandbox.client.RuntimeModel;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CLIParser {

  private static final Logger LOG = LoggerFactory.getLogger(CLIParser.class.getName());

  @Autowired PropertiesParser pp;

  /**
   * Parse arguments from the command line, updating the runtime model if required.
   * @param model the runtime model
   * @param args the command line arguments
   * @return the updated runtime model
   */
  public RuntimeModel parseCommandLine(RuntimeModel model, String[] args) {
    Options options = getOptions();
    // parse the arguments
    try {
      CommandLineParser parser = new DefaultParser();
      CommandLine cmd = parser.parse(options, args);
      // check for config
      if (cmd.hasOption("config")) {
        pp.parseConfiguration(model, cmd.getOptionValue("config"));
      }
    } catch (ParseException e) {
      LOG.warn("Error parsing command line arguments", e);
    }
    return model;
  }

  /**
   * Generate and return the options object.
   * <p>This discribes the different options available to the user.
   * @return The Options object.
   */
  public static Options getOptions() {
    Options options = new Options();

    options.addOption(
        Option.builder("config")
            .hasArg()
            .argName("file")
            .desc("Read from a configuration file")
            .build());

    return options;
  }

  /** Print the default options text. */
  public void printHelp() {
    Options options = getOptions();
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("java -jar AISandbox.jar", options);
  }
}
