package nl.bertriksikken.luchtmeetnet.api.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

public final class StationTest {

    @Test
    public void testDeserialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = getClass().getClassLoader().getResource("station.json");
        Station station = mapper.readValue(url, Station.class);
        StationData stationData = station.getData();
        double[] coordinates = stationData.getCoordinates();
        Assert.assertEquals(5.82224, coordinates[0], 0.00001);
        Assert.assertEquals(50.98445, coordinates[1], 0.00001);
    }

}
