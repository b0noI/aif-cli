package com.aif.language.sentence;

import java.util.List;
import com.aif.language.sentence.StatSentenceSeparatorExtractor.CharacterStat;
import com.aif.language.token.TokenSplitter;

public class SentencesSeparatorExtractor {

    private static final String PRINT_CHARACTER_STAT = "Character: [ %s ] Probability: %f";

    private final String text;

    public SentencesSeparatorExtractor(String text) {
        this.text = text;
    }

    public void extractAndShow() {
        final TokenSplitter tokenSplitter = new TokenSplitter();
        final StatSentenceSeparatorExtractor sentenceSeparatorExtractor = (StatSentenceSeparatorExtractor)ISentenceSeparatorExtractor.Type.STAT.getInstance();
        final List<String> tokens = tokenSplitter.split(text);
        final List<CharacterStat> characterStats = sentenceSeparatorExtractor.getCharactersStat(tokens);
        characterStats.forEach(ch -> System.out.println(String.format(PRINT_CHARACTER_STAT, ch.getCharacter(), ch.getProbabilityThatEndCharacter())));
    }

}
