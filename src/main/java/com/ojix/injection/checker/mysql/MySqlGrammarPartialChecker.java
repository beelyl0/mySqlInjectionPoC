package com.ojix.injection.checker.mysql;

import antlr.MySqlLexer;
import antlr.MySqlParser;
import com.ojix.injection.api.GrammarPartialChecker;
import com.ojix.injection.api.GrammarPartialPrefix;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

public final class MySqlGrammarPartialChecker implements GrammarPartialChecker {

    private static GrammarPartialPrefix[] PREFIXES = new GrammarPartialPrefix[]{
            new GrammarPartialPrefix("SELECT * FROM A WHERE A='", 14, 2),
            new GrammarPartialPrefix("SELECT * FROM A WHERE A=", 13, 2)
    };

    public MySqlGrammarPartialChecker() {
    }

    public boolean partialCheck(String input) {
        for (GrammarPartialPrefix prefix : PREFIXES) {
            StringBuilder in = new StringBuilder();
            in.append(prefix.getPrefix());
            in.append(input);
            MySqlLexerWithCounting lexer = new MySqlLexerWithCounting(CharStreams.fromString(in.toString()));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MySqlParser parser = new MySqlParser(tokens);
            parser.setBuildParseTree(false);
            MySqlParserErrorListener errorListener = new MySqlParserErrorListener();
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);
            parser.root();
            int tokensNumber = lexer.getTokensCount() - prefix.getTokensNumber();
            if (!errorListener.hasError() && tokensNumber > prefix.getTokensThreshold()) {
                return true;
            }
        }
        return false;
    }

    public int getTokensNumber(String input) {
        MySqlLexer lexer = new MySqlLexer((CharStreams.fromString(input)));
        int tokensNumber = 0;
        for (Token token = lexer.nextToken(); token.getType() != Token.EOF; token = lexer.nextToken()) {
            ++tokensNumber;
        }
        return tokensNumber;
    }

}
