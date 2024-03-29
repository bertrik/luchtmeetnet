package nl.bertriksikken.luchtmeetnet.api.dto;

import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import nl.bertriksikken.geojson.FeatureCollection.PointGeometry;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class StationData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("description")
    private MultiLingualText description;

    @JsonProperty("geometry")
    private PointGeometry geometry;

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

    public PointGeometry getGeometry() {
        return geometry;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{location=%s, type=%s, geometry=%s}", location, type, geometry);
    }

}
