package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.cli.common.ResultPrinter;
import io.aif.language.common.*;
import io.aif.language.semantic.ISemanticDict;
import io.aif.language.semantic.ISemanticNode;
import io.aif.language.semantic.SemanticDictBuilder;
import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassifier;
import io.aif.language.sentence.separators.extractors.ISeparatorExtractor;
import io.aif.language.sentence.separators.groupers.ISeparatorsGrouper;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.word.IWord;
import io.aif.language.word.dict.DictBuilder;
import io.aif.language.word.dict.WordPlaceHolderMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by olehkozlovskyi on 04.03.15.
 */
public class SemanticDictBuildCommand extends BasicTextCommand{


    @Override
    public Void apply(String[] args) {

        final String text;
        try {
            text = FileHelper.readAllTextFromFile(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        final TokenSplitter tokenSplitter = new TokenSplitter();
        final AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        final List<String> tokens = tokenSplitter.split(text);
        final List<List<String>> sentences = sentenceSplitter.split(tokens);
        final List<String> filteredTokens = sentences.stream().flatMap(List::stream).collect(Collectors.toList());

        final IDictBuilder dictBuilder = new DictBuilder();
        final IDict<IWord> dict = dictBuilder.build(filteredTokens);
        final IMapper<Collection<String>, List<IWord.IWordPlaceholder>> toWordPlaceHolderMapper = new WordPlaceHolderMapper((ISearchable<String, IWord>)dict);
        final List<IWord.IWordPlaceholder> placeholders = toWordPlaceHolderMapper.map(filteredTokens);

        final ISeparatorExtractor testInstance = ISeparatorExtractor.Type.PROBABILITY.getInstance();
        final ISeparatorsGrouper separatorsGrouper = ISeparatorsGrouper.Type.PROBABILITY.getInstance();
        final ISeparatorGroupsClassifier sentenceSeparatorGroupsClassificatory = ISeparatorGroupsClassifier.Type.PROBABILITY.getInstance();
        final List<Character> separators = testInstance.extract(tokens).get();
        final Map<ISeparatorGroupsClassifier.Group, Set<Character>> grouppedSeparators = sentenceSeparatorGroupsClassificatory.classify(tokens, separatorsGrouper.group(tokens, separators));


        final SemanticDictBuilder semanticDictBuilder = new SemanticDictBuilder(grouppedSeparators);
        final ISemanticDict semanticDict = semanticDictBuilder.build(placeholders);

        final List<ISemanticNode<IWord>> sortedNodes = semanticDict.getWords().stream().sorted((w2, w1) -> ((Double) w1.weight()).compareTo(w2.weight())).collect(Collectors.toList());

        ResultPrinter.PrintSemanticDictionaryBuildResult(sortedNodes);

        return null;
    }


}
