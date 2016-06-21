package com.aif.language.sentence;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.aif.cli.common.FileHelper;
import com.aif.cli.common.ResultPrinter;
import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassifier;
import io.aif.language.sentence.separators.extractors.ISeparatorExtractor;
import io.aif.language.sentence.separators.groupers.ISeparatorsGrouper;
import io.aif.language.token.TokenSplitter;

class SentencesSeparatorExtractorCommand extends BasicTextCommand {

    private static final String NO_SEPARATOR_WERE_FOUND      = "No separators were found";

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
        final ISeparatorExtractor separatorExtractor = ISeparatorExtractor.Type.PROBABILITY.getInstance();
        final ISeparatorsGrouper separatorsGrouper = ISeparatorsGrouper.Type.PROBABILITY.getInstance();
        final ISeparatorGroupsClassifier sentenceSeparatorGroupsClassificatory = ISeparatorGroupsClassifier.Type.PROBABILITY.getInstance();

        final List<String> tokens = tokenSplitter.split(text);
        final Optional<List<Character>> optSeparators = separatorExtractor.extract(tokens);
        if (!optSeparators.isPresent()) {
            System.out.println(NO_SEPARATOR_WERE_FOUND);
            return null;
        }
        final List<Character> separators = optSeparators.get();

        final List<Set<Character>> separatorsGroupsUnclasify = separatorsGrouper.group(tokens, separators);
        final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separatorsGroups = sentenceSeparatorGroupsClassificatory.classify(tokens, separatorsGroupsUnclasify);
        ResultPrinter.printSentenceSeparatorSplitResult(separatorsGroups);
        return null;
    }



}
