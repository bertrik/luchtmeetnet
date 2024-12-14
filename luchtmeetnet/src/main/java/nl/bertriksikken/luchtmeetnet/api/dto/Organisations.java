package nl.bertriksikken.luchtmeetnet.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Organisations extends PagedResponse<Organisations.Data> {

    public record Data(
            @JsonProperty("id") int id,
            @JsonProperty("name") MultiLingualText name) {

    }

}
