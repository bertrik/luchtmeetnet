package nl.bertriksikken.luchtmeetnet;

import java.util.HashMap;
import java.util.Set;

public final class MultiLingualText extends HashMap<String, String> {

    private static final long serialVersionUID = 9220425002735398337L;

    public static final String ENGLISH = "EN";
    public static final String DUTCH = "NL";

    // jackson constructor
    @SuppressWarnings("unused")
    private MultiLingualText() {
        super();
    }
    
    MultiLingualText(MultiLingualText text) {
        putAll(text);
    }
    
    public Set<String> getLanguages() {
        return Set.copyOf(keySet());
    }

    public String get(String language) {
        return getOrDefault(language, "???");
    }

}
