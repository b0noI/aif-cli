package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.cli.common.ResultPrinter;
import io.aif.language.common.ISplitter;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;

import java.io.IOException;
import java.util.List;

class SentenceSplitCommand extends BasicTextCommand {

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
        ResultPrinter.printSentenceSplitResult(result);
        return null;
    }
}
