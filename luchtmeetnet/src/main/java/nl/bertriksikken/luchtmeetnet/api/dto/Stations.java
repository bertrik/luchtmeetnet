package nl.bertriksikken.luchtmeetnet.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Stations extends PagedResponse<Stations.Data> {

    public record Data(@JsonProperty("number") String number,
                       @JsonProperty("location") String location) {
    }
}
