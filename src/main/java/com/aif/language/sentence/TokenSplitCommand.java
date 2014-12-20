package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import io.aif.language.token.TokenSplitter;

import java.io.IOException;
import java.util.List;

class TokenSplitCommand extends BasicTextCommand {

    private static final String TOKEN_TEMPLATE = "Token: [ %s ]";

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
        result.forEach(token -> System.out.println(String.format(TOKEN_TEMPLATE, token)));
        return null;

    }

}
