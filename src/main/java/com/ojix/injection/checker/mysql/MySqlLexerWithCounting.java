package com.ojix.injection.checker.mysql;

import antlr.MySqlLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;

public class MySqlLexerWithCounting extends MySqlLexer {

    private int tokensCount;

    public MySqlLexerWithCounting(CharStream input) {
        super(input);
    }

    @Override
    public Token nextToken() {
        ++tokensCount;
        return super.nextToken();
    }

    public int getTokensCount() {
        return tokensCount;
    }

}
