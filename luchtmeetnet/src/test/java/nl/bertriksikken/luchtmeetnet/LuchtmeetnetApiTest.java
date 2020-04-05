package nl.bertriksikken.luchtmeetnet;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.bertriksikken.luchtmeetnet.api.ILuchtmeetnetRestApi;
import nl.bertriksikken.luchtmeetnet.api.LuchtmeetnetApi;
import nl.bertriksikken.luchtmeetnet.api.dto.ComponentsData;
import nl.bertriksikken.luchtmeetnet.api.dto.MeasurementData;
import nl.bertriksikken.luchtmeetnet.api.dto.OrganisationData;
import nl.bertriksikken.luchtmeetnet.api.dto.StationData;

public final class LuchtmeetnetApiTest {

    private final Logger LOG = LoggerFactory.getLogger(LuchtmeetnetApiTest.class);

    public static void main(String[] args) throws IOException {
        LuchtmeetnetApiTest test = new LuchtmeetnetApiTest();
        test.run();
    }

    private void run() throws IOException {
        ILuchtmeetnetRestApi restApi = LuchtmeetnetApi.newRestClient("https://api.luchtmeetnet.nl/open_api/",
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

        // get a list of all stations
        List<String> numbers = api.getStationNumbers();
        LOG.info("Found {} stations", numbers.size());

        // show information for a specific station
        String number = numbers.get(0);
        StationData stationData = api.getStationData(number);
        LOG.info("Station {}: {}", number, stationData);
        
        // get all measurements from the past hour
        Instant now = Instant.now();
        List<MeasurementData> measurementData = api.getMeasurements(now);
        LOG.info("Found {} neasurements for the past hour", measurementData.size());

    }

}
