package com.ojix.injection.processor;

@FunctionalInterface
public interface AnalyzerLineProcessor {

    void process(int lineNum, String line);

}
