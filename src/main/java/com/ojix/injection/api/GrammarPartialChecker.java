package com.ojix.injection.api;

public interface GrammarPartialChecker {

    boolean partialCheck(String input);

    int getTokensNumber(String input);

}
