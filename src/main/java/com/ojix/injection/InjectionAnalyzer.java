package com.ojix.injection;

import com.ojix.injection.api.GrammarPartialChecker;
import com.ojix.injection.checker.mysql.MySqlGrammarPartialChecker;

import java.util.*;

public final class InjectionAnalyzer {

    private InjectionAnalyzer() {
    }

    private static final Map<Class<? extends GrammarPartialChecker>, InjectionType> grammarToInjectionTypeMap = new HashMap<Class<? extends GrammarPartialChecker>, InjectionType>() {{
        put(MySqlGrammarPartialChecker.class, InjectionType.SQL);
    }};

    public static Set<InjectionType> check(String input) {
        Set<InjectionType> result = null;
        for (Map.Entry<Class<? extends GrammarPartialChecker>, InjectionType> entry : grammarToInjectionTypeMap.entrySet()) {
            try {
                GrammarPartialChecker grammarPartialChecker = entry.getKey().newInstance();
                if (grammarPartialChecker.partialCheck(input)) {
                    result = (result == null) ? EnumSet.noneOf(InjectionType.class) : result;
                    result.add(entry.getValue());
                }
            } catch (InstantiationException|IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (result == null) ? Collections.emptySet() : result;
    }
}
