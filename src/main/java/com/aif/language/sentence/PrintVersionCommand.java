package com.aif.language.sentence;

class PrintVersionCommand extends BasicCommandWithoutArguments {

    private static final String AIF_VERSION = "0.0.0-Alpha1";
    private static final String CLI_VERSION = "1.0";

    @Override
    public Void apply(String... s) {
        System.out.printf("CLI version: %s , AIF: %s", CLI_VERSION, AIF_VERSION);
        return null;
    }

    @Override
    public boolean validate(String... args) {
        return true;
    }
}
