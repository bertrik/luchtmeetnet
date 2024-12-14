package nl.bertriksikken.luchtmeetnet;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Components extends PagedResponse<Components.Data> {

    public record Data(@JsonProperty("formula") String formula,
                       @JsonProperty("name") MultiLingualText name) {
    }

}
