package com.aif.language.sentence;

import java.util.function.Function;

/**
 * Created by b0noI on 07/09/14.
 */
public interface ICommand extends Function<String[], Void> {

    public boolean validate(String... args);

    public enum Commands {

        SENTENCE_SPLIT("ssplit", new SentenceSplitCommand(), "Split text to sentences"),
        SENTENCE_SEPARATOR_EXTRACTOR("ess", new SentencesSeparatorExtractorCommand(), "Extract sentences separators"),
        STAMMER_EXTRACTOR("estam", new StammerExtractorCommand(), "Extract stammers from text"),
        TOKEN_SPLIT("tsplit", new TokenSplitCommand(), "Split text to tokens"),
        TOKEN_SEPARATOR_EXTRACTOR("ets", new TokenSeparatorExtractorCommand(), "Get token separator unicode hex"),
        HELP("help", new PrintHelpCommand(), "Print this message"),
        VERSION("v", new PrintVersionCommand(), "Print CLI and AIF versions");


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
