package com.aif.language.sentence;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

abstract class BasicTextCommand implements ICommand {

    @Override
    public boolean validate(String... args) {
        if (args.length == 0 ) {
            System.out.println("There is no path");
            return false;
        }

        final Path path = Paths.get(args[0]);
        if (!Files.exists(path)) {
            System.out.printf("Path: %s not exists\n", path);
            return false;
        }

        return true;
    }

}
