package com.aif.cli.common;

import com.sun.javafx.binding.StringFormatter;
import io.aif.language.semantic.ISemanticNode;
import io.aif.language.word.IWord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class FileHelper {

    private static String filePath;

    public static String readAllTextFromFile(final String path) throws IOException {
        filePath = path;
        try(    final InputStream is = new FileInputStream(path);
                final BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            final StringBuffer buff = new StringBuffer();

            String line = null;

            while((line = reader.readLine()) != null) {
                buff.append(line + System.lineSeparator());
            }

            return buff.toString();
        }
    }

    public static void saveSemanticResult(List<ISemanticNode<IWord>> result) {

        final String template = "Semantic node: %s, with weight = %.5f. \n";

        try (   final OutputStream os = new FileOutputStream(filePath+"semanticResult.txt");
                final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os)))
            {


            result.forEach( s-> {
                try {
                    bw.write(StringFormatter.format(template, s.item().getRootToken(), s.weight()).getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
