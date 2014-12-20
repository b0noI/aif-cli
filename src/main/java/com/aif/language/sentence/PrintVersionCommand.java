package com.aif.language.sentence;

import io.aif.language.common.settings.ISettings;

class PrintVersionCommand extends BasicCommandWithoutArguments {

    private static final String AIF_VERSION = ISettings.SETTINGS.getVersion();
    private static final String CLI_VERSION = "1.1";

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
