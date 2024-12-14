package nl.bertriksikken.luchtmeetnet.api.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bertriksikken.luchtmeetnet.Station;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

public final class StationTest {

    @Test
    public void testDeserialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = getClass().getClassLoader().getResource("station.json");
        Station station = mapper.readValue(url, Station.class);
        Station.Data stationData = station.data();
        double[] coordinates = stationData.geometry().coordinates();
        Assertions.assertEquals(5.82224, coordinates[0], 0.00001);
        Assertions.assertEquals(50.98445, coordinates[1], 0.00001);
    }

}
