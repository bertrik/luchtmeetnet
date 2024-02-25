package nl.bertriksikken.luchtmeetnet;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bertriksikken.luchtmeetnet.api.ILuchtmeetnetRestApi;
import nl.bertriksikken.luchtmeetnet.api.LuchtmeetnetApi;
import nl.bertriksikken.luchtmeetnet.api.dto.ComponentsData;
import nl.bertriksikken.luchtmeetnet.api.dto.MeasurementData;
import nl.bertriksikken.luchtmeetnet.api.dto.OrganisationData;
import nl.bertriksikken.luchtmeetnet.api.dto.StationData;
import nl.bertriksikken.luchtmeetnet.api.dto.StationsData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LuchtmeetnetApiTest {

    private final Logger LOG = LoggerFactory.getLogger(LuchtmeetnetApiTest.class);

    public static void main(String[] args) throws IOException {
        LuchtmeetnetApiTest test = new LuchtmeetnetApiTest();
        test.run();
    }

    private void run() throws IOException {
        ILuchtmeetnetRestApi restApi = LuchtmeetnetApi.newRestClient("https://api.luchtmeetnet.nl",
                Duration.ofSeconds(10));
        LuchtmeetnetApi api = new LuchtmeetnetApi(restApi);

        // get a list of all organisations
        List<OrganisationData> organisations = api.getOrganisations();
        LOG.info("Found {} organisations", organisations.size());
        for (OrganisationData organisation : organisations) {
            LOG.info("Organisation: {}", organisation);
        }

        // get a list of all components
        List<ComponentsData> components = api.getComponents();
        LOG.info("Found {} components", components.size());
        for (ComponentsData component : components) {
            LOG.info("Component: {}", component);
        }

        // get LKI
        Instant now = Instant.now();
        List<MeasurementData> lki = api.getLki(now);
        LOG.info("Found {} LKI measurements", lki.size());
        
        // get all measurements from the past hour
        List<MeasurementData> measurementData = api.getMeasurements("", now);
        LOG.info("Found {} measurements for the past hour", measurementData.size());
        measurementData.addAll(lki);

        // get a list of all stations
        List<StationsData> stations = api.getStations();
        LOG.info("Found {} stations", stations.size());

        // get a list of all station details mentioned in the measurement data
        Map<String, StationData> stationDataMap = new HashMap<>();
        for (MeasurementData data : measurementData) {
            String stationNumber = data.getStationNumber();
            if (!stationDataMap.containsKey(stationNumber)) {
                StationData stationData = api.getStationData(stationNumber);
                if (stationData != null) {
                    stationDataMap.put(stationNumber, stationData);
                }
            }
        }
        LOG.info("Retrieved station info for {} stations", stationDataMap.keySet().size());

        File file = new File("geojson.json");
        GeoJsonWriter writer = new GeoJsonWriter();
        writer.writeGeoJson(file, stationDataMap, measurementData);
    }

}
