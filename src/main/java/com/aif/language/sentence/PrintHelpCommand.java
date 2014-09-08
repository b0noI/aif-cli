package com.aif.language.sentence;

import com.aif.cli.Main;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

class PrintHelpCommand extends BasicCommandWithoutArguments {

    @Override
    public Void apply(String... args) {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "aif-cli [key] <path>", Main.createCLIOptions() );
        return null;
    }

    @Override
    public boolean validate(String... args) {
        return true;
    }

}
