package nl.bertriksikken.geojson;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import nl.bertriksikken.geojson.FeatureCollection.Feature;

public final class FeatureCollectionTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testSerialize() throws JsonProcessingException {
        GeoJsonGeometry geometry = new GeoJsonGeometry.Point(0.0, 0.0);
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
        GeoJsonGeometry.Point geometry = new GeoJsonGeometry.Point(12.34, 56.78);
        String json = MAPPER.writeValueAsString(geometry);
        // deserialize
        GeoJsonGeometry.Point decoded = MAPPER.readValue(json, GeoJsonGeometry.Point.class);
        Assert.assertEquals(12.34, decoded.getLatitude(), 0.01);
        Assert.assertEquals(56.78, decoded.getLongitude(), 0.01);
    }

}
