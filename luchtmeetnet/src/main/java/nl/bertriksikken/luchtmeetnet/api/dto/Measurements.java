package nl.bertriksikken.luchtmeetnet.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Measurements extends PagedResponse<Measurements.Data> {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(
            @JsonProperty("station_number") String stationNumber,
            @JsonProperty("value") double value,
            @JsonProperty("formula") String formula,
            @JsonProperty("timestamp_measured") String timeStamp) {
    }

}
