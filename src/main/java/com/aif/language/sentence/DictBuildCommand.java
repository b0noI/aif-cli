package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import io.aif.language.token.TokenSplitter;
import io.aif.language.word.IWord;
import io.aif.language.word.dict.DictBuilder;
import io.aif.language.word.dict.IDictBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

class DictBuildCommand extends BasicTextCommand {

    private static final String WORD_OUTPUT_TEMPLATE = "Basic token: %s tokens: [ %s ]";

    private void printWord(final IWord word) {
        System.out.println(String.format(WORD_OUTPUT_TEMPLATE, word.getRootToken(), word.getAllTokens()));
    }

    @Override
    public Void apply(String... args) {
        final String text;
        try {
            text = FileHelper.readAllTextFromFile(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        final TokenSplitter tokenSplitter = new TokenSplitter();
        final IDictBuilder<Collection<String>> stemmer = new DictBuilder();
        final Set<IWord> result = stemmer.build(tokenSplitter.split(text)).getWords();
        result.forEach(this::printWord);
        return null;
    }
}
