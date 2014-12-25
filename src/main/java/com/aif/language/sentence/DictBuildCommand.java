package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.cli.common.ResultPrinter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.word.IWord;
import io.aif.language.word.dict.DictBuilder;
import io.aif.language.word.dict.IDictBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

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
        final IDictBuilder<Collection<String>> stemmer = new DictBuilder();
        final Set<IWord> result = stemmer.build(tokenSplitter.split(text)).getWords();
        ResultPrinter.PrintStammerExtrctResult(result);
        return null;
    }
}
