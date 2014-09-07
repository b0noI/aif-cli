package com.aif.language.sentence;

import java.util.List;
import com.aif.language.sentence.StatSentenceSeparatorExtractor.CharacterStat;
import com.aif.language.token.TokenSplitter;

class SentencesSeparatorExtractorCommand implements ICommand {

    private static final String PRINT_CHARACTER_STAT = "Character: [ %s ] Probability: %f";

    @Override
    public Void apply(final String text) {
        final TokenSplitter tokenSplitter = new TokenSplitter();
        final StatSentenceSeparatorExtractor sentenceSeparatorExtractor = (StatSentenceSeparatorExtractor)ISentenceSeparatorExtractor.Type.STAT.getInstance();
        final List<String> tokens = tokenSplitter.split(text);
        final List<CharacterStat> characterStats = sentenceSeparatorExtractor.getCharactersStat(tokens);
        characterStats.forEach(ch -> System.out.println(String.format(PRINT_CHARACTER_STAT, ch.getCharacter(), ch.getProbabilityThatEndCharacter())));
        return null;
    }
}
