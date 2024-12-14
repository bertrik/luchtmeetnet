package nl.bertriksikken.luchtmeetnet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Station(@JsonProperty("data") Data data) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(@JsonProperty("location") String location,
                       @JsonProperty("description") MultiLingualText description,
                       @JsonProperty("geometry") Geometry geometry,
                       @JsonProperty("type") String type,
                       @JsonProperty("municipality") String municipality,
                       @JsonProperty("organisation") String organisation,
                       @JsonProperty("components") List<String> components) {

        @SuppressWarnings("ArrayRecordComponent")
        public record Geometry(@JsonProperty("type") String type,
                               @JsonProperty("coordinates") double[] coordinates) {
            public Geometry {
                type = Objects.requireNonNullElse(type, "");
                coordinates = Objects.requireNonNullElse(coordinates, new double[0]).clone();
            }
        }
    }
}
