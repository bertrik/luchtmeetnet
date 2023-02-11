package nl.bertriksikken.luchtmeetnet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import nl.bertriksikken.geojson.FeatureCollection;
import nl.bertriksikken.geojson.FeatureCollection.Feature;
import nl.bertriksikken.geojson.FeatureCollection.PointGeometry;
import nl.bertriksikken.luchtmeetnet.api.dto.MeasurementData;
import nl.bertriksikken.luchtmeetnet.api.dto.StationData;

public final class GeoJsonWriter {

    public void writeGeoJson(File file, Map<String, StationData> stationDataMap, List<MeasurementData> measurements)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        FeatureCollection collection = new FeatureCollection();
        for (Entry<String, StationData> entry : stationDataMap.entrySet()) {
            String stationNr = entry.getKey();
            StationData stationData = entry.getValue();

            PointGeometry stationGeometry = stationData.getGeometry();
            Feature feature = new Feature(
                    new PointGeometry(stationGeometry.getLatitude(), stationGeometry.getLongitude()));
            collection.add(feature);

            // add station info to properties
            ObjectNode stationNode = mapper.createObjectNode();
            feature.addProperty("station", stationNode);
            feature.addProperty("number", stationNr);
            feature.addProperty("location", stationData.getLocation());

            // add all component values as properties
            Map<String, Double> components = new HashMap<>();
            feature.addProperty("components", components);
            List<MeasurementData> stationMeasurements = measurements.stream()
                    .filter(m -> m.getStationNumber().equals(stationNr)).collect(Collectors.toList());
            for (MeasurementData data : stationMeasurements) {
                components.put(data.getFormula(), data.getValue());
            }
        }

        mapper.writeValue(file, collection);
    }

}
