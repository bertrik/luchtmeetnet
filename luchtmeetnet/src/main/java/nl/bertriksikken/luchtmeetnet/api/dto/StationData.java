package nl.bertriksikken.luchtmeetnet.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("UnusedVariable")
@JsonIgnoreProperties(ignoreUnknown = true)
public final class StationData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("description")
    private MultiLingualText description;

    @JsonProperty("geometry")
    private Geometry geometry;

    @JsonProperty("type")
    private String type;

    @JsonProperty("municipality")
    private String municipality;

    @JsonProperty("organisation")
    private String organisation;

    @JsonProperty("components")
    private List<String> components;

    public String getLocation() {
        return location;
    }

    public double[] getCoordinates() {
        return geometry.coordinates;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{location=%s, type=%s, geometry=%s}", location, type, geometry);
    }

    private static final class Geometry {
        @JsonProperty("type")
        String type = "";
        @JsonProperty("coordinates")
        double[] coordinates = new double[0];

        @Override
        public String toString() {
            return String.format(Locale.ROOT, "{type=%s,coords=%s", type, Arrays.toString(coordinates));
        }
    }


}
