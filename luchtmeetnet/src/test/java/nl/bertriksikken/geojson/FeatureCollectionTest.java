package nl.bertriksikken.geojson;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import nl.bertriksikken.geojson.FeatureCollection.Feature;
import nl.bertriksikken.geojson.FeatureCollection.GeoJsonGeometry;
import nl.bertriksikken.geojson.FeatureCollection.PointGeometry;

public final class FeatureCollectionTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testSerialize() throws JsonProcessingException {
        GeoJsonGeometry geometry = new PointGeometry(0.0, 0.0);
        Feature feature = new Feature(geometry);
        FeatureCollection collection = new FeatureCollection();
        collection.add(feature);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(collection);
        System.out.println(json);
    }

    @Test
    public void testGeometry() throws JsonProcessingException {
        // serialize
        PointGeometry geometry = new PointGeometry(12.34, 56.78);
        String json = MAPPER.writeValueAsString(geometry);
        // deserialize
        PointGeometry decoded = MAPPER.readValue(json, PointGeometry.class);
        Assert.assertEquals(12.34, decoded.getLatitude(), 0.01);
        Assert.assertEquals(56.78, decoded.getLongitude(), 0.01);
    }

}
