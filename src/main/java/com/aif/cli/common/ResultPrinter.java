package com.aif.cli.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    result.forEach(s -> {
      if (!stop) {
        System.out.println(String.format(template, sentenceToString(s)));
        length--;
        count++;
        stopper();
      }
    });

  }

  public static void printSentenceSeparatorSplitResult(Map<ISeparatorGroupsClassifier.Group, Set<Character>> result) {
    final String template = "Group: \'%s\', characters: %s\n";
    length = result.size();
    result.keySet().forEach(s -> {
      if (!stop) {
        System.out.println(String.format(template, s, charactersToString(result.get(s))));
        length--;
        count++;
        stopper();
      }
    });
  }

  public static void printTokeSplitResult(List<String> result) {
    final String template = "Token: [ %s ]";
    length = result.size();
    result.forEach(token -> {
      if (!stop) {
        System.out.println(String.format(template, token));
        length--;
        count++;
        stopper();
      }
    });
  }

  public static void printSeparatorExtractResult(List<Character> result) {
    final String template = "Separator: \\u%s";
    length = result.size();
    result.forEach(separator -> {
      if (!stop) {
        System.out.println(String.format(template, Integer.toHexString(separator)));
        length--;
        count++;
        stopper();
      }
    });
  }

  public static void printStammerExtrctResult(IDict<IWord> result) {
    final String template = "Basic token: %-20s count: [ %-10d ] tokens: [ %s ]";

    length = result.getWords().size();
    List<IWord> words = new ArrayList<>(result.getWords());
    words.sort((w1, w2) -> w2.getCount().compareTo(w1.getCount()));
    words.forEach(word -> {
      if (!stop) {
        System.out.println(String.format(template, word.getRootToken(), word.getCount(), word.getAllTokens()));
        length--;
        count++;
        stopper();
      }
    });
  }

  public static void printSemanticDictionaryBuildResult(AssociationGraph<IWord> result) {
    final String template = "Semantic node: %-20s, with weight = %.5f.";

    length = result.getVertices().size();
    List<IWord> words = new ArrayList<>(result.getVertices());
    words.sort((w1, w2) -> result.getVertexWeight(w2).compareTo(result.getVertexWeight(w1)));
    words.forEach(word -> {
      if (!stop) {
        System.out.println(String.format(template, word.getRootToken(), result.getVertexWeight(word)));
        length--;
        count++;
        stopper();
      }
    });

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

        if (command.equals("")) {
          count = 0;
          clicked = true;
        } else if (command.equals("q")) {
          stop = true;
          return;
        }
      }
    }
  }

  private static String sentenceToString(List<String> sentence) {
    final StringBuilder sentenceBuilder = new StringBuilder();
    for (String token : sentence) {
      if (sentenceBuilder.length() > 1)
        sentenceBuilder.append(" ");
      sentenceBuilder.append(token);
    }

    return sentenceBuilder.toString();
  }

  private static String charactersToString(final Set<Character> characters) {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('[');
    characters.forEach(ch -> stringBuilder.append(ch + " "));
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    stringBuilder.append(']');
    return stringBuilder.toString();
  }

}
