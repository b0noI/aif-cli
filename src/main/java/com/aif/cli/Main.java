package com.aif.cli;

import com.aif.language.sentence.ICommand;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class Main {

  public static void main(final String... args) throws IOException {
    final Options options = Main.createCLIOptions();
    try {
      final CommandLine commandLine = Main.createCommandLineParser(options, args);
      commandLinePrecheck(commandLine);
      for (ICommand.Commands command : ICommand.Commands.values()) {
        if (commandLine.hasOption(command.getCommandKey())
            && command.getCommand().validate(commandLine.getArgs())) {
          command.getCommand().apply(commandLine.getArgs());
          return;
        }
      }
    } catch (ParseException e) {
      System.out.printf("ERROR: %s ; Try -help \n", e.getMessage());
    }
  }

  public static Options createCLIOptions() {
    final Options options = new Options();
    for (ICommand.Commands command : ICommand.Commands.values()) {
      options.addOption(command.getCommandKey(), false, command.getHelpLine());
    }

    return options;
  }

  private static void commandLinePrecheck(final CommandLine commandLine) throws ParseException {
    if (commandLine.getOptions().length == 0) {
      throw new ParseException("Command key is not provided");
    }

    if (commandLine.getOptions().length > 1) {
      throw new ParseException("There are too many options");
    }
  }

  private static CommandLine createCommandLineParser(final Options options,
                                                     final String... args) throws ParseException {
    final CommandLineParser parser = new BasicParser();
    return parser.parse(options, args);
  }

}
