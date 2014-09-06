package com.aif.cli.common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileHelper {

    public static String readAllTextFromFile(final String path) throws IOException {
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

}
