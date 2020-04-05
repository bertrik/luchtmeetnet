package nl.bertriksikken.luchtmeetnet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import nl.bertriksikken.luchtmeetnet.api.dto.MeasurementData;
import nl.bertriksikken.luchtmeetnet.api.dto.StationData;

public final class GeoJsonWriter {

    public void writeGeoJson(File file, Map<String, StationData> stationDataMap, List<MeasurementData> measurementData)
            throws IOException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("type", "FeatureCollection");
        ArrayNode features = mapper.createArrayNode();
        root.set("features", features);
        for (Entry<String, StationData> entry : stationDataMap.entrySet()) {
            String stationNr = entry.getKey();
            StationData stationData = entry.getValue();
            
            ObjectNode feature = mapper.createObjectNode();
            features.add(feature);
            feature.put("type", "Feature");

            ObjectNode geometry = mapper.createObjectNode();
            feature.set("geometry", geometry);
            geometry.put("type", "Point");
            ArrayNode coordinates = mapper.createArrayNode();
            geometry.set("coordinates", coordinates);
            coordinates.add(stationData.getGeometry().getLatitude());
            coordinates.add(stationData.getGeometry().getLongitude());

            // add all component values as properties
            ObjectNode properties = mapper.createObjectNode();
            feature.set("properties", properties);
            ArrayNode components = mapper.createArrayNode();
            properties.set("components", components);
            ObjectNode component = mapper.createObjectNode();
            components.add(component);
            component.put("NO2", 1.234);
            properties.put("station_number", stationNr);
        }

        mapper.writeValue(file, root);
    }

}
