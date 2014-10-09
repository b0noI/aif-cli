package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.language.common.ISplitter;
import com.aif.language.sentence.splitters.AbstractSentenceSplitter;
import com.aif.language.token.TokenSplitter;
import com.aif.language.word.AbstractWord;
import com.aif.language.word.IWordExtractor;
import com.aif.language.word.WordExtractor;

import java.io.IOException;
import java.util.List;

public class StammerExtractorCommand extends BasicTextCommand {

    private static final String STAMMER_TEMPLATE = "Stammer: [ %s ]";

    private void printListOfStammers(List<AbstractWord.WordPlaceHolder> listOfStammers) {
        for(AbstractWord.WordPlaceHolder stammer: listOfStammers) {
            printStammer(stammer);
        }
    }

    private void printStammer(AbstractWord.WordPlaceHolder stammer) {

        System.out.println(String.format(STAMMER_TEMPLATE, stammer));

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
        final ISplitter<List<String>, List<String>> sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        final IWordExtractor wordExtractor = new WordExtractor();
        final List<List<AbstractWord.WordPlaceHolder>> result = wordExtractor.getWords(sentenceSplitter.split(tokenSplitter.split(text)));
        result.forEach(this::printListOfStammers);
        return null;
    }
}
