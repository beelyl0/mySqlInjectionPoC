package com.ojix.injection.processor;

import com.ojix.injection.InjectionAnalyzer;
import com.ojix.injection.InjectionType;

import java.io.PrintStream;
import java.util.Set;

public class AnalyzerPlainTextProcessor implements AnalyzerLineProcessor {

    private final PrintStream out;

    public AnalyzerPlainTextProcessor(PrintStream out) {
        this.out = out;
    }

    @Override
    public void process(int lineNum, String line) {
        if (line != null && !line.isEmpty()) {
            Set<InjectionType> injResult = InjectionAnalyzer.check(line.toUpperCase());
            if (!injResult.isEmpty()) {
                out.println(injResult + " N:[" + lineNum + "] DATA:" + line);
            }
        }
    }

}
