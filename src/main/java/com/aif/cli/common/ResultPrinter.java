package com.aif.cli.common;


import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassificatory;
import io.aif.language.word.IWord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ResultPrinter {

    private static final int MAX_SINGLE_OUTPUT = 50;
    private static final List<String> res = new ArrayList<>();
    private static int count = 0;
    private static int length = 0;
    private static boolean stop = false;

    public static void PrintSentenceSplitResult(List<List<String>> result) {

        final String template = "Sentence: [ %s ]";

        length = result.size();

        result.forEach(s -> {

            if(!stop) {
                System.out.println(String.format(template, sentenceToString(s)));
                length--;
                count++;
                stopper();
            }

        });

    }

    public static void PrintSentenceSeparatorSplitResult(Map<ISeparatorGroupsClassificatory.Group, Set<Character>> result) {

        final String template = "Group: \'%s\', characters: %s\n";

        length = result.size();

        result.keySet().forEach(s -> {

            if(!stop) {
                System.out.println(String.format(template, s, charactersToString(result.get(s))));
                length--;
                count++;
                stopper();
            }

        });



    }

    public static void PrintTokeSplitResult(List<String> result) {

        final String template = "Token: [ %s ]";

        length = result.size();

        result.forEach(token -> {

            if(!stop) {
                System.out.println(String.format(template, token));
                length--;
                count++;
                stopper();
            }

        });

    }

    public static void PrintSeparatorExtractResult(List<Character> result) {

        final String template = "Separator: \\u%s";

        result.forEach(separator -> {

            if(!stop) {
                System.out.println(String.format(template, Integer.toHexString(separator)));
                length--;
                count++;
                stopper();
            }

        });

    }

    public static void PrintStammerExtrctResult(Set<IWord> result) {

        final String template = "Basic token: %s tokens: [ %s ]";

        result.forEach(word -> {

            if(!stop) {
                System.out.println(String.format(template, word.getRootToken(), word.getAllTokens()));
                length--;
                count++;
                stopper();
            }

        });

    }

    private static void stopper() {

        boolean clicked = false;

            if(count > MAX_SINGLE_OUTPUT) {

                while (!clicked) {
                    System.out.printf("Press 'Enter' to continue or 'q' command to quit. There are %d entities to show \n", length);

                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String command = null;

                    try {
                        command = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(command.equals("")) {
                        count = 0;
                        clicked = true;
                    }else if( command.equals("q")) {
                        stop = true;
                        return;
                    }

                }

            }

    }

    private static String sentenceToString(List<String> sentence) {
        final StringBuilder sentenceBuilder = new StringBuilder();
        for(String token: sentence) {
            if(sentenceBuilder.length() > 1)
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
