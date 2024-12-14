package nl.bertriksikken.luchtmeetnet;

import nl.bertriksikken.luchtmeetnet.api.LuchtmeetnetClient;
import nl.bertriksikken.luchtmeetnet.api.dto.Components;
import nl.bertriksikken.luchtmeetnet.api.dto.Measurements;
import nl.bertriksikken.luchtmeetnet.api.dto.Organisations;
import nl.bertriksikken.luchtmeetnet.api.dto.Station;
import nl.bertriksikken.luchtmeetnet.api.dto.Stations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RunLuchtmeetnetClient {

    private final Logger LOG = LoggerFactory.getLogger(RunLuchtmeetnetClient.class);

    public static void main(String[] args) throws Exception {
        RunLuchtmeetnetClient test = new RunLuchtmeetnetClient();
        try (LuchtmeetnetClient client = LuchtmeetnetClient.create(
                "https://api.luchtmeetnet.nl", Duration.ofSeconds(10))) {
            test.run(client);
        }
    }

    private void run(LuchtmeetnetClient client) throws IOException {
        // get a list of all organisations
        List<Organisations.Data> organisations = client.getOrganisations();
        LOG.info("Found {} organisations", organisations.size());
        for (Organisations.Data organisation : organisations) {
            LOG.info("Organisation: {}", organisation);
        }

        // get a list of all components
        List<Components.Data> components = client.getComponents();
        LOG.info("Found {} components", components.size());
        for (Components.Data component : components) {
            LOG.info("Component: {}", component);
        }

        // get LKI
        Instant now = Instant.now();
        List<Measurements.Data> lki = client.getLki(now);
        LOG.info("Found {} LKI measurements", lki.size());

        // get all measurements from the past hour
        List<Measurements.Data> measurementData = client.getMeasurements("", now);
        LOG.info("Found {} measurements for the past hour", measurementData.size());
        measurementData.addAll(lki);

        // get a list of all stations
        List<Stations.Data> stations = client.getStations();
        LOG.info("Found {} stations", stations.size());

        // get a list of all station details mentioned in the measurement data
        Map<String, Station.Data> stationDataMap = new HashMap<>();
        for (Measurements.Data data : measurementData) {
            String stationNumber = data.stationNumber();
            if (!stationDataMap.containsKey(stationNumber)) {
                Station.Data stationData = client.getStationData(stationNumber);
                if (stationData != null) {
                    stationDataMap.put(stationNumber, stationData);
                }
            }
        }
        LOG.info("Retrieved station info for {} stations", stationDataMap.size());

        File file = new File("geojson.json");
        GeoJsonWriter writer = new GeoJsonWriter();
        writer.writeGeoJson(file, stationDataMap, measurementData);

        LOG.info("Done writing");
    }

}
