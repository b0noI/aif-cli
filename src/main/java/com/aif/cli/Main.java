package com.aif.cli;

import com.aif.cli.common.FileHelper;
import com.aif.language.sentence.ICommand;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String... args) throws IOException {
        final Options options = Main.createCLIOptions();
        try {
            final CommandLine commandLine = Main.createCommandLineParser(options, args);
            for (ICommand.Commands command : ICommand.Commands.values()) {
                if (commandLine.hasOption(command.getCommandKey())) {
                    command.getCommand().validate(commandLine.getArgs());
                    command.getCommand().apply(commandLine.getArgs());
                    return;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.printf("ERROR: %s ; Try --help", e.getMessage());
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
        if (commandLine.getArgs().length == 0) {
            throw new ParseException("There is no path");
        }
        if (commandLine.getArgs().length > 1) {
            throw new ParseException("There are too many arguments");
        }
        final Path path = Paths.get(commandLine.getArgs()[0]);
        if (!Files.exists(path)) {
            throw new ParseException(String.format("Path: %s not exists", path));
        }
    }

    private static CommandLine createCommandLineParser(final Options options, final String... args) throws ParseException {
        final CommandLineParser parser = new BasicParser();
        return parser.parse( options, args);
    }

}
