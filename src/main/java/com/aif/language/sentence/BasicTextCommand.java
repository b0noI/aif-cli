package com.aif.language.sentence;

abstract class BasicTextCommand implements ICommand {

    @Override
    public boolean validate(String... args) {
        if (args.length != 0 ) {
            return false;
        }
        return true;
    }

}
