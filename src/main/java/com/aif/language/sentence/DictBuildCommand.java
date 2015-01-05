package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.cli.common.ResultPrinter;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IDict;
import io.aif.language.word.IWord;
import io.aif.language.word.comparator.ISetComparator;
import io.aif.language.word.dict.DictBuilder;
import io.aif.language.word.dict.IDictBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class DictBuildCommand extends BasicTextCommand {

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
        final AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        final List<String> tokens = tokenSplitter.split(text);
        final List<List<String>> sentences = sentenceSplitter.split(tokens);
        final List<String> filteredTokens = sentences.stream().flatMap(List::stream).collect(Collectors.toList());

        final ITokenComparator tokenComparator = ITokenComparator.defaultComparator();
        final ISetComparator setComparator = ISetComparator.createDefaultInstance(tokenComparator);
        final DictBuilder dictBuilder = new DictBuilder(setComparator, tokenComparator);
        final IDict dict = dictBuilder.build(filteredTokens);

        ResultPrinter.PrintStammerExtrctResult(dict.getWords());
        return null;
    }
}
