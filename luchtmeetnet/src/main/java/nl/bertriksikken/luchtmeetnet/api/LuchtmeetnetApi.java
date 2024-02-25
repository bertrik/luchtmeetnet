package nl.bertriksikken.luchtmeetnet.api;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import nl.bertriksikken.luchtmeetnet.api.dto.ComponentsData;
import nl.bertriksikken.luchtmeetnet.api.dto.MeasurementData;
import nl.bertriksikken.luchtmeetnet.api.dto.OrganisationData;
import nl.bertriksikken.luchtmeetnet.api.dto.Station;
import nl.bertriksikken.luchtmeetnet.api.dto.StationData;
import nl.bertriksikken.luchtmeetnet.api.dto.StationsData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public final class LuchtmeetnetApi {

    private static final Logger LOG = LoggerFactory.getLogger(LuchtmeetnetApi.class);

    private final ILuchtmeetnetRestApi api;

    public LuchtmeetnetApi(ILuchtmeetnetRestApi api) {
        this.api = api;
    }

    public static ILuchtmeetnetRestApi newRestClient(String url, Duration timeout) {
        LOG.info("Creating new REST client for URL '{}' with timeout {}", url, timeout);
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(timeout).writeTimeout(timeout)
                .readTimeout(timeout).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(JacksonConverterFactory.create())
                .client(client).build();
        return retrofit.create(ILuchtmeetnetRestApi.class);
    }

    /**
     * Retrieves all station metadata.
     * 
     * @return a list of station numbers
     * @throws IOException in case of a problem fetching the data
     */
    public List<StationsData> getStations() throws IOException {
        PagedResponseFetcher<StationsData> fetcher = new PagedResponseFetcher<>(10);
        return fetcher.fetch((page) -> api.getStations(page).execute().body());
    }

    public List<OrganisationData> getOrganisations() throws IOException {
        PagedResponseFetcher<OrganisationData> fetcher = new PagedResponseFetcher<>(10);
        return fetcher.fetch((page) -> api.getOrganisations(page).execute().body());
    }

    public List<ComponentsData> getComponents() throws IOException {
        PagedResponseFetcher<ComponentsData> fetcher = new PagedResponseFetcher<>(10);
        return fetcher.fetch((page) -> api.getComponents(page).execute().body());
    }

    /**
     * Retrieves the station data for a specific station.
     * 
     * @param number the station number
     * @return the station data, null if not found
     * @throws IOException in case of a problem fetching the data
     */
    public StationData getStationData(String number) throws IOException {
        Response<Station> response = api.getStation(number).execute();
        if (!response.isSuccessful()) {
            LOG.warn("Failed to retrieve station data for '{}': {}", number, response.errorBody().string());
            return null;
        }
        Station station = response.body();
        return station.getData();
    }

    public List<MeasurementData> getStationMeasurements(String number, String formula) throws IOException {
        PagedResponseFetcher<MeasurementData> fetcher = new PagedResponseFetcher<>(100);
        return fetcher.fetch((page) -> api.getStationMeasurements(number, page, formula).execute().body());
    }

    public List<MeasurementData> getMeasurements(String formula, Instant instant) throws IOException {
        Instant endTime = instant.truncatedTo(ChronoUnit.SECONDS);
        Instant startTime = endTime.minus(Duration.ofMinutes(70));
        PagedResponseFetcher<MeasurementData> fetcher = new PagedResponseFetcher<>(100);
        return fetcher.fetch((page) -> api.getMeasurements(page, formula, startTime, endTime).execute().body());
    }

    public List<MeasurementData> getLki(Instant instant) throws IOException {
        Instant endTime = instant.truncatedTo(ChronoUnit.SECONDS);
        Instant startTime = endTime.minus(Duration.ofMinutes(70));
        PagedResponseFetcher<MeasurementData> fetcher = new PagedResponseFetcher<>(10);
        return fetcher.fetch((page) -> api.getLki(page, startTime, endTime).execute().body());
    }

}
