package dev.aisandbox.client.cli;

import dev.aisandbox.client.RuntimeModel;
import org.apache.commons.cli.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CLIRuntimeModelFactory {

    private static final Logger LOG = Logger.getLogger(CLIRuntimeModelFactory.class.getName());

    public static RuntimeModel parseCommandLine(RuntimeModel model, String[] args) {
        Options options = getOptions();
        // parse the arguments
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            // check for config
            if (cmd.hasOption("config")) {
                XMLRuntimeModelFactory.parseCommandLine(model, cmd.getOptionValue("config"));
            }
        } catch (ParseException e) {
            LOG.log(Level.WARNING, "Error parsing command line arguments", e);
        }
        return model;
    }

    public static Options getOptions() {
        Options options = new Options();

        options.addOption(
                Option.builder("config")
                        .hasArg()
                        .argName("file")
                        .desc("Read from a configuration file")
                        .build()
        );

        return options;
    }

    /**
     * Print the default options text
     */
    public static void printHelp() {
        Options options = getOptions();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar AISandbox.jar", options);
    }
}