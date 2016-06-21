package com.aif.cli.common;

import com.sun.javafx.binding.StringFormatter;
import io.aif.associations.builder.AssociationGraph;
import io.aif.language.word.IWord;

import java.io.*;

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

    public static void saveSemanticResult(AssociationGraph<IWord> result) {

        final String template = "Semantic node: %s, with weight = %.5f. \n";

        try (   final OutputStream os = new FileOutputStream(filePath+"semanticResult.txt");
                final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os)))
            {


            result.getVertices().forEach( s-> {
                try {
                    bw.write(StringFormatter.format(template, s.getRootToken(), result.getVertexWeight(s)).getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
