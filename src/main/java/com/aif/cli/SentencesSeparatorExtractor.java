package com.aif.cli;

import java.util.List;
import com.aif.language.sentence.ISentenceSeparatorExtractor;
import com.aif.language.token.TokenSplitter;

class SentencesSeparatorExtractor {

    private final String text;

    SentencesSeparatorExtractor(String text) {
        this.text = text;
    }

    public List<Character> extract() {
        final TokenSplitter tokenSplitter = new TokenSplitter();
        final ISentenceSeparatorExtractor sentenceSeparatorExtractor = ISentenceSeparatorExtractor.Type.STAT.getInstance();
        return sentenceSeparatorExtractor.extract(tokenSplitter.split(text)).get();
    }

}
