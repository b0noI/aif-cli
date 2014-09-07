package com.aif.language.sentence;

import java.util.function.Function;

/**
 * Created by b0noI on 07/09/14.
 */
public interface ICommand extends Function<String, Void> {

    public enum Commands {

        SENTENCE_SPLIT("ssplit", new SentenceSplitCommand(), "Split text to sentences"),
        SENTENCE_SEPARATOR_EXTRACTOR("ess", new SentencesSeparatorExtractorCommand(), "Extract sentences separators"),
        HELP("help", new PrintHelpCommand(), "Print this message");

        private final String commandKey;

        private final ICommand command;

        private final String helpLine;

        Commands(final String commandKey, final ICommand command, final String helpLine) {
            this.commandKey = commandKey;
            this.command = command;
            this.helpLine = helpLine;
        }

        public String getCommandKey() {
            return commandKey;
        }

        public ICommand getCommand() {
            return command;
        }

        public String getHelpLine() {
            return helpLine;
        }

    }

}
