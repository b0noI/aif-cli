package com.aif.language.sentence;

import java.util.function.Function;

/**
 * Created by b0noI on 07/09/14.
 */
public interface ICommand extends Function<String, Void> {

    public enum Commands {

        SENTENCE_SPLIT("ssplit", new SentenceSplitCommand()),
        SENTENCE_SEPARATOR_EXTRACTOR("ess", new SentencesSeparatorExtractorCommand());

        private final String commandKey;

        private final ICommand command;

        Commands(final String commandKey, final ICommand command) {
            this.commandKey = commandKey;
            this.command = command;
        }

        public String getCommandKey() {
            return commandKey;
        }

        public ICommand getCommand() {
            return command;
        }

    }

}
