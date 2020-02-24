package com.ojix.injection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import com.ojix.injection.processor.AnalyzerLineProcessor;
import com.ojix.injection.processor.AnalyzerPlainTextProcessor;
import com.ojix.injection.processor.AnalyzerUriProcessor;

import java.io.*;
import java.util.Scanner;

public class Main {

    @Parameter(names={"--input", "-i"}, converter = FileConverter.class)
    File input;

    @Parameter(names={"--parse-url", "-u"})
    boolean needUrlParse = false;

    @Parameter(names={"--output", "-o"}, converter = FileConverter.class)
    File output;

    public static void main(String[] args) {
        System.out.println("-=+ Find SQL Injection tool +=-");

        Main main = new Main();

        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        try {
            InputStream in = (main.input != null) ? new FileInputStream(main.input) : System.in;

            PrintStream out = (main.output != null) ? new PrintStream(main.output) : System.out;

            AnalyzerLineProcessor processor = (main.needUrlParse) ? new AnalyzerUriProcessor(out) : new AnalyzerPlainTextProcessor(out);

            main.run(in, processor);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void run(InputStream in, AnalyzerLineProcessor processor) {
        Scanner scanner = new Scanner(in);
        int lineNum = 1;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            processor.process(lineNum, line);
            ++lineNum;
        }
    }

}
