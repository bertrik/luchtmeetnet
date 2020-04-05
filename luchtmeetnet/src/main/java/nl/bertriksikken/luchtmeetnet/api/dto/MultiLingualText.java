package nl.bertriksikken.luchtmeetnet.api.dto;

import java.util.HashMap;
import java.util.Set;

public final class MultiLingualText extends HashMap<String, String> {

    private static final long serialVersionUID = 9220425002735398337L;

    public static final String ENGLISH = "EN";
    public static final String DUTCH = "NL";

    public Set<String> getLanguages() {
        return super.keySet();
    }

    public String get(String language) {
        String text = super.get(language);
        if (text == null) {
            text = "<text unavailable>";
        }
        return text;
    }

}
