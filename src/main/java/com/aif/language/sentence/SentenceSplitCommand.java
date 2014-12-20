package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import io.aif.language.common.ISplitter;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;

import java.io.IOException;
import java.util.List;

class SentenceSplitCommand extends BasicTextCommand {

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
    public Void apply(final String... args) {
        final String text;
        try {
            text = FileHelper.readAllTextFromFile(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        final TokenSplitter tokenSplitter = new TokenSplitter();
        final ISplitter<List<String>, List<String>> sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        final List<List<String>> result = sentenceSplitter.split(tokenSplitter.split(text));
        result.forEach(this::buildSentenceAndPrint);
        return null;
    }
}
