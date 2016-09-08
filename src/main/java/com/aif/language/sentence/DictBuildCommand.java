package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.cli.common.ResultPrinter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import io.aif.language.common.IDict;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.word.dict.DictBuilder;

class DictBuildCommand extends BasicTextCommand {

  @Override
  public Void apply(String... args) {
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

    final DictBuilder dictBuilder = new DictBuilder();
    final IDict dict = dictBuilder.build(filteredTokens);

    ResultPrinter.printStammerExtrctResult(dict);
    return null;
  }
}
