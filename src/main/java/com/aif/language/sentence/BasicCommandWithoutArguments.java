package com.aif.language.sentence;

abstract class BasicCommandWithoutArguments implements ICommand {

  @Override
  public boolean validate(String... args) {
    return true;
  }
}
