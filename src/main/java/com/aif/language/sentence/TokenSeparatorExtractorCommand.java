package com.aif.language.sentence;

import com.aif.language.token.ITokenSeparatorExtractor;

import java.util.List;
import java.util.Optional;

public class TokenSeparatorExtractorCommand implements ICommand {
    private static final String SEPARATOR_TEMPLATE = "Separator: \\u%s";
    private static final String NO_SEPARATORS_MESSAGE = "No token separators found in text.";

    private Void convertAndPrint(Character separator) {

        System.out.println(String.format(SEPARATOR_TEMPLATE, Integer.toHexString(separator)));

        return null;
    }

    @Override
    public Void apply(final String text) {
        final ITokenSeparatorExtractor extractor = ITokenSeparatorExtractor.Type.PROBABILITY.getInstance();
        final Optional<List<Character>> separators = extractor.extract(text);

        if(!separators.isPresent()) {

            System.out.println(NO_SEPARATORS_MESSAGE);
        } else {

            separators.get().forEach(this::convertAndPrint);
        }

        return null;
    }

}
