package com.aif.language.sentence;

import java.io.IOException;
import java.util.List;

import com.aif.cli.common.FileHelper;
import com.aif.language.sentence.StatSentenceSeparatorExtractor.CharacterStat;
import com.aif.language.token.TokenSplitter;

class SentencesSeparatorExtractorCommand extends BasicTextCommand {

    private static final String PRINT_CHARACTER_STAT = "Character: [ %s ] Probability: %f";

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
        final StatSentenceSeparatorExtractor sentenceSeparatorExtractor = (StatSentenceSeparatorExtractor)ISentenceSeparatorExtractor.Type.STAT.getInstance();
        final List<String> tokens = tokenSplitter.split(text);
        final List<CharacterStat> characterStats = sentenceSeparatorExtractor.getCharactersStat(tokens);
        characterStats.forEach(ch -> System.out.println(String.format(PRINT_CHARACTER_STAT, ch.getCharacter(), ch.getProbabilityThatEndCharacter())));
        return null;
    }
}
