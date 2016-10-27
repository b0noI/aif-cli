package com.aif.cli.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.aif.associations.builder.AssociationGraph;
import io.aif.language.common.IDict;
import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassifier;
import io.aif.language.word.IWord;

public class ResultPrinter {

  private static final int MAX_SINGLE_OUTPUT = 50;
  private static int count = 0;
  private static int length = 0;
  private static boolean stop = false;

  public static void printSentenceSplitResult(List<List<String>> result) {
    final String template = "Sentence: [ %s ]";
    length = result.size();
    result.forEach(s -> printIfNotStop(String.format(template, sentenceToString(s))));
  }

  public static void printSentenceSeparatorSplitResult(Map<ISeparatorGroupsClassifier.Group, Set<Character>> result) {
    final String template = "Group: \'%s\', characters: %s\n";
    length = result.size();
    result.keySet().forEach(s -> printIfNotStop(String.format(template, s, charactersToString(result.get(s)))));
  }

  public static void printTokeSplitResult(List<String> result) {
    final String template = "Token: [ %s ]";
    length = result.size();
    result.forEach(token -> printIfNotStop(String.format(template, token)));
  }

  public static void printSeparatorExtractResult(List<Character> result) {
    final String template = "Separator: \\u%s";
    length = result.size();
    result.forEach(separator -> printIfNotStop(String.format(template, Integer.toHexString(separator))));
  }

  public static void printStammerExtrctResult(IDict<IWord> result) {
    final String template = "Basic token: %-20s count: [ %-10d ] tokens: [ %s ]";

    length = result.getWords().size();
    result.getWords()
      .stream()
      .sorted((w1, w2) -> w2.getCount().compareTo(w1.getCount()))
      .forEach(word -> printIfNotStop(String.format(template, word.getRootToken(), word.getCount(), word.getAllTokens())));
  }

  public static void printSemanticDictionaryBuildResult(AssociationGraph<IWord> result) {
    final String template = "Semantic node: %-20s, with weight = %.5f.";

    length = result.getVertices().size();
    result.getVertices()
      .stream()
      .sorted((w1, w2) -> result.getVertexWeight(w2).compareTo(result.getVertexWeight(w1)))
      .forEach(word -> printIfNotStop(String.format(template, word.getRootToken(), result.getVertexWeight(word))));
    FileHelper.saveSemanticResult(result);
  }


  private static void stopper() {
    boolean clicked = false;
    if (count > MAX_SINGLE_OUTPUT) {
      while (!clicked) {
        System.out.printf("Press 'Enter' to continue or 'q' command to quit. There are %d entities to show \n", length);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String command = null;

        try {
          command = br.readLine();
        } catch (IOException e) {
          e.printStackTrace();
        }

        if ("".equals(command)) {
          count = 0;
          clicked = true;
        } else if ("q".equals(command)) {
          stop = true;
          return;
        }
      }
    }
  }

  private static String sentenceToString(List<String> sentence) {
    return sentence.stream().collect(Collectors.joining(" "));
  }

  private static String charactersToString(final Set<Character> characters) {
    return '[' + characters.stream().map(String::valueOf).collect(Collectors.joining(" ")) + ']';
  }

  private static void printIfNotStop(String s) {
    if (!stop) {
      System.out.println(s);
      length--;
      count++;
      stopper();
    }
  }

}
