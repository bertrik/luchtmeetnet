package nl.bertriksikken.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import nl.bertriksikken.geojson.FeatureCollection;
import nl.bertriksikken.geojson.FeatureCollection.Feature;
import nl.bertriksikken.geojson.GeoJsonGeometry;
import nl.bertriksikken.luchtmeetnet.Measurements;
import nl.bertriksikken.luchtmeetnet.Station;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class GeoJsonWriter {

    public void writeGeoJson(File file, Map<String, Station.Data> stationDataMap, List<Measurements.Data> measurements)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        FeatureCollection collection = new FeatureCollection();
        for (Entry<String, Station.Data> entry : stationDataMap.entrySet()) {
            String stationNr = entry.getKey();
            Station.Data stationData = entry.getValue();

            double[] coordinates = stationData.geometry().coordinates();
            Feature feature = new Feature(new GeoJsonGeometry.Point(coordinates[1], coordinates[0]));
            collection.add(feature);

            // add station info to properties
            Map<String, String> station = new HashMap<>();
            feature.addProperty("station", station);
            station.put("number", stationNr);
            station.put("location", stationData.location());

            // add all component values as properties
            Map<String, Double> components = new HashMap<>();
            feature.addProperty("components", components);
            List<Measurements.Data> stationMeasurements = measurements.stream()
                    .filter(m -> m.stationNumber().equals(stationNr)).toList();
            for (Measurements.Data data : stationMeasurements) {
                components.put(data.formula(), data.value());
            }
        }

        mapper.writeValue(file, collection);
    }

}
