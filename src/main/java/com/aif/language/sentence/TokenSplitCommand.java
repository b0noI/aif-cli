package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.cli.common.ResultPrinter;
import io.aif.language.token.TokenSplitter;

import java.io.IOException;
import java.util.List;

class TokenSplitCommand extends BasicTextCommand {

    @Override
    public Void apply(final String... args) {
        String text = null;
        try {
            text = FileHelper.readAllTextFromFile(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        final TokenSplitter tokenSplitter = new TokenSplitter();
        final List<String> result = tokenSplitter.split(text);
        ResultPrinter.printTokeSplitResult(result);
        return null;

    }

}
