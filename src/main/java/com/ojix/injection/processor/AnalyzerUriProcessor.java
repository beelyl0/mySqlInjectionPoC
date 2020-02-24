package com.ojix.injection.processor;

import com.ojix.injection.InjectionAnalyzer;
import com.ojix.injection.InjectionType;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

public final class AnalyzerUriProcessor implements AnalyzerLineProcessor {

    private final PrintStream out;

    public AnalyzerUriProcessor(PrintStream out) {
        this.out = out;
    }

    @Override
    public void process(int lineNum, String line) {
        try {
            URI uri = new URI(line);
            boolean hasInjections = false;
            List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(uri, Charset.defaultCharset());
            for (NameValuePair nameValue : nameValuePairs) {
                if (nameValue.getValue() != null && nameValue.getValue().length() > 0) {
                    Set<InjectionType> injResult = InjectionAnalyzer.check(nameValue.getValue().toUpperCase());
                    if (!injResult.isEmpty()) {
                        if (!hasInjections) {
                            out.println(injResult + " N:[" + lineNum + "] URL:" + line);
                            hasInjections = true;
                        }
                        out.println("NAME:" + nameValue.getName());
                        out.println("VALUE:" + nameValue.getValue());
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
