package com.ojix.injection.api;

public final class GrammarPartialPrefix {

    private final String prefix;

    private final int tokensNumber;

    private final int tokensThreshold;

    public GrammarPartialPrefix(String prefix, int tokensNumber, int tokensThreshold) {
        this.prefix = prefix;
        this.tokensNumber = tokensNumber;
        this.tokensThreshold = tokensThreshold;
    }

    public final String getPrefix() {
        return prefix;
    }

    public final int getTokensNumber() {
        return tokensNumber;
    }

    public final int getTokensThreshold() {
        return tokensThreshold;
    }

}
