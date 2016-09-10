package com.aif.language.sentence;

import com.aif.cli.common.FileHelper;
import com.aif.cli.common.ResultPrinter;

import java.io.IOException;
import java.util.List;

import io.aif.language.token.TokenSplitter;

class TokenSplitCommand extends BasicTextCommand {

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
    final List<String> result = tokenSplitter.split(text);
    ResultPrinter.printTokeSplitResult(result);
    return null;
  }

}
