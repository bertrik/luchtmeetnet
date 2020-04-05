package nl.bertriksikken.luchtmeetnet.api.dto;

import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class StationData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("description")
    private BilingualText description;
    
    @JsonProperty("geometry")
    private PointGeometry geometry;
    
    @JsonProperty("type")
    private String type;

    @JsonProperty("components")
    private List<String> components;

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{location=%s, type=%s, geometry=%s}", location, type, geometry);
    }

}
