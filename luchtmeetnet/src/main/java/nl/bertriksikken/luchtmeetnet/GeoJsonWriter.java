package nl.bertriksikken.luchtmeetnet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import nl.bertriksikken.luchtmeetnet.api.dto.MeasurementData;
import nl.bertriksikken.luchtmeetnet.api.dto.StationData;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class GeoJsonWriter {

    public void writeGeoJson(File file, Map<String, StationData> stationDataMap, List<MeasurementData> measurements)
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
            coordinates.add(stationData.getGeometry().getLongitude());
            coordinates.add(stationData.getGeometry().getLatitude());

            // feature properties
            ObjectNode properties = mapper.createObjectNode();
            feature.set("properties", properties);

            // add station info to properties
            ObjectNode stationNode = mapper.createObjectNode();
            properties.set("station", stationNode);
            stationNode.put("number", stationNr);
            stationNode.put("location", stationData.getLocation());

            // add all component values as properties
            ObjectNode components = mapper.createObjectNode();
            properties.set("components", components);
            List<MeasurementData> stationMeasurements = measurements.stream()
                    .filter(m -> m.getStationNumber().equals(stationNr)).collect(Collectors.toList());
            for (MeasurementData data : stationMeasurements) {
                components.put(data.getFormula(), data.getValue());
            }
        }

        mapper.writeValue(file, root);
    }

}
