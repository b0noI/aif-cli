package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.language.token.TokenSplitter;
import com.aif.language.word.AbstractWord;
import com.aif.language.word.IWord;
import com.aif.language.word.stemmer.Stemmer;

import java.io.IOException;
import java.util.List;

class StammerExtractorCommand extends BasicTextCommand {

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
        final Stemmer stemmer = new Stemmer();
        final List<IWord> result = stemmer.extract(tokenSplitter.split(text)).get();
        result.forEach(this::printWord);
        return null;
    }
}
