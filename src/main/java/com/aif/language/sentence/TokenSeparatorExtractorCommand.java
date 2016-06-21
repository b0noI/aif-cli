package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.cli.common.ResultPrinter;
import io.aif.language.token.ITokenSeparatorExtractor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

class TokenSeparatorExtractorCommand extends BasicTextCommand {
    private static final String NO_SEPARATORS_MESSAGE = "No token separators found in text.";

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

            ResultPrinter.printSeparatorExtractResult(separators.get());
        }

        return null;
    }

}
