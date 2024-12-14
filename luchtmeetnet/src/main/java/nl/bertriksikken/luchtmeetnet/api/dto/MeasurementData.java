package nl.bertriksikken.luchtmeetnet.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MeasurementData(
        @JsonProperty("station_number") String stationNumber,
        @JsonProperty("value") double value,
        @JsonProperty("formula") String formula,
        @JsonProperty("timestamp_measured") String timeStamp) {
}
