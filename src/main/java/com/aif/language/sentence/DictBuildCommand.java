package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.language.token.TokenSplitter;
import com.aif.language.word.IWord;
import com.aif.language.word.dict.DictBuilder;
import com.aif.language.word.dict.IDictBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
        final List<IWord> result = stemmer.build(tokenSplitter.split(text));
        result.forEach(this::printWord);
        return null;
    }
}
