package com.aif.language.sentence;

import com.aif.language.token.TokenSplitter;

import java.util.List;

class TokenSplitCommand implements ICommand {

    private static final String TOKEN_TEMPLATE = "Token: [ %s ]";

    @Override
    public Void apply(final String text) {

        final TokenSplitter tokenSplitter = new TokenSplitter();
        final List<String> result = tokenSplitter.split(text);
        result.forEach(token -> System.out.println(String.format(TOKEN_TEMPLATE, token)));
        return null;

    }

}
