package com.ojix.injection.checker.mysql;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class MySqlParserErrorListener extends BaseErrorListener {

    private volatile boolean hasError;

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        hasError = true;
    }

    public boolean hasError() {
        return hasError;
    }

}
