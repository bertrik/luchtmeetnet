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

            PointGeometry geometry = stationData.getGeometry();
            Feature feature = new Feature(new PointGeometry(geometry.getLatitude(), geometry.getLongitude()));
            collection.add(feature);

            // add station info to properties
            Map<String, String> station = new HashMap<>();
            feature.addProperty("station", station);
            station.put("number", stationNr);
            station.put("location", stationData.getLocation());

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
