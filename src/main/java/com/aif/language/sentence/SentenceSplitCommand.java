package com.aif.language.sentence;

import com.aif.language.common.ISplitter;
import com.aif.language.token.TokenSplitter;

import java.util.List;

public class SentenceSplitCommand {
    private static final String SENTENCE_TEMPLATE = "Sentence: [ %s ]";
    private final String text;

    public SentenceSplitCommand(final String text) {
        this.text = text;
    }

    public void sentenceSplitAndShow() {
        final TokenSplitter tokenSplitter = new TokenSplitter();
        final ISplitter<List<String>, List<String>> sentenceSplitter = new SentenceSplitter();
        final List<List<String>> result = sentenceSplitter.split(tokenSplitter.split(text));
        result.forEach(sent -> System.out.println(String.format(SENTENCE_TEMPLATE, sentenceToString(sent))));
    }

    private String sentenceToString(List<String> sentence) {
        final StringBuilder sentenceBuilder = new StringBuilder();
        for(String token: sentence) {
            if(sentenceBuilder.length() > 1)
                sentenceBuilder.append(" ");
            sentenceBuilder.append(token);
        }
        return sentenceBuilder.toString();
    }
}
