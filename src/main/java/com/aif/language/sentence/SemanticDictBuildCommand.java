package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.cli.common.ResultPrinter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import io.aif.associations.builder.AssociationGraph;
import io.aif.language.common.IDict;
import io.aif.language.common.IDictBuilder;
import io.aif.language.common.IMapper;
import io.aif.language.common.ISearchable;
import io.aif.language.semantic.SemanticGraphBuilder;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.word.IWord;
import io.aif.language.word.dict.DictBuilder;
import io.aif.language.word.dict.WordPlaceHolderMapper;

class SemanticDictBuildCommand extends BasicTextCommand {

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
    final AbstractSentenceSplitter sentenceSplitter =
        AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
    final List<String> tokens = tokenSplitter.split(text);
    final List<List<String>> sentences = sentenceSplitter.split(tokens);
    final List<String> filteredTokens =
        sentences.stream().flatMap(List::stream).collect(Collectors.toList());

    final IDictBuilder<Collection<String>, IWord> dictBuilder = new DictBuilder();
    final IDict<IWord> dict = dictBuilder.build(filteredTokens);
    final IMapper<Collection<String>, List<IWord.IWordPlaceholder>> toWordPlaceHolderMapper =
        new WordPlaceHolderMapper((ISearchable<String, IWord>) dict);
    final List<IWord.IWordPlaceholder> placeholders = toWordPlaceHolderMapper.map(filteredTokens);

    final SemanticGraphBuilder semanticGraphBuilder = new SemanticGraphBuilder();
    final AssociationGraph<IWord> semanticDict = semanticGraphBuilder.build(placeholders);

    ResultPrinter.printSemanticDictionaryBuildResult(semanticDict);

    return null;
  }


}
