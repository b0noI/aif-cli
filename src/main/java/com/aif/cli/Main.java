package com.aif.cli;

import com.aif.cli.common.FileHelper;
import com.aif.language.sentence.SentenceSplitCommand;
import com.aif.language.sentence.SentencesSeparatorExtractor;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static final String ESS_KEY = "ess";
    private static final String SENTENCE_SPLIT_KEY = "ssplit";
    private static final String HELP_KEY = "help";

    public static void main(String... args) throws IOException {
        final Options options = Main.createCLIOptions();
        try {
            final CommandLine commandLine = Main.createCommandLineParser(options, args);
            if (commandLine.hasOption(HELP_KEY)) {
                showHelp(options);
                return;
            }
            Main.commandLinePrecheck(commandLine);
            Main.executeCommandLine(commandLine);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.printf("ERROR: %s ; Try --help", e.getMessage());
        }
    }

    private static void showHelp(final Options options) {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "aif-cli [key] <path>", options );
    }

    private static void executeCommandLine(final CommandLine commandLine) throws IOException {
        final String path = commandLine.getArgs()[0];
        final String text = FileHelper.readAllTextFromFile(path);
        if (commandLine.hasOption(ESS_KEY)) {
            executeESS(text);
        }
        if(commandLine.hasOption(SENTENCE_SPLIT_KEY)) {
            executeSSplit(text);
        }
    }

    private static void executeSSplit(final String text) {
        new SentenceSplitCommand(text).sentenceSplitAndShow();
    }

    private static void executeESS(final String text) {
        new SentencesSeparatorExtractor(text).extractAndShow();
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

    private static Options createCLIOptions() {
        final Options options = new Options();

        options.addOption(HELP_KEY, false, "print this message" );
        options.addOption(ESS_KEY, false, "Extract sentences separators");
        options.addOption(SENTENCE_SPLIT_KEY, false, "Split text to sentences");

        return options;
    }

    private static CommandLine createCommandLineParser(final Options options, final String... args) throws ParseException {
        final CommandLineParser parser = new BasicParser();
        return parser.parse( options, args);
    }

}
