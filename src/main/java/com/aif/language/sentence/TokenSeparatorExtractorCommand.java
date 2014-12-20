package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import io.aif.language.token.ITokenSeparatorExtractor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

class TokenSeparatorExtractorCommand extends BasicTextCommand {
    private static final String SEPARATOR_TEMPLATE = "Separator: \\u%s";
    private static final String NO_SEPARATORS_MESSAGE = "No token separators found in text.";

    private Void convertAndPrint(Character separator) {

        System.out.println(String.format(SEPARATOR_TEMPLATE, Integer.toHexString(separator)));

        return null;
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
