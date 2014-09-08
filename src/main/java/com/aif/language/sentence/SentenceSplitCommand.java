package com.aif.language.sentence;

import com.aif.language.common.ISplitter;
import com.aif.language.token.TokenSplitter;

import java.util.List;

class SentenceSplitCommand implements ICommand {

    private static final String SENTENCE_TEMPLATE = "Sentence: [ %s ]";

    private String sentenceToString(List<String> sentence) {
        final StringBuilder sentenceBuilder = new StringBuilder();
        for(String token: sentence) {
            if(sentenceBuilder.length() > 1)
                sentenceBuilder.append(" ");
            sentenceBuilder.append(token);
        }
        return sentenceBuilder.toString();
    }

    private void buildSentenceAndPrint(List<String> sentence) {

        System.out.println(String.format(SENTENCE_TEMPLATE, sentenceToString(sentence)));
    }

    @Override
    public Void apply(final String text) {
        final TokenSplitter tokenSplitter = new TokenSplitter();
        final ISplitter<List<String>, List<String>> sentenceSplitter = new SentenceSplitter();
        final List<List<String>> result = sentenceSplitter.split(tokenSplitter.split(text));
        result.forEach(this::buildSentenceAndPrint);
        return null;
    }
}
