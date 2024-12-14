package nl.bertriksikken.luchtmeetnet.api;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

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

public final class LuchtmeetnetClient implements AutoCloseable{

    private static final Logger LOG = LoggerFactory.getLogger(LuchtmeetnetClient.class);

    private final ILuchtmeetnetRestApi restApi;
    private final OkHttpClient httpClient;

    LuchtmeetnetClient(OkHttpClient httpClient, ILuchtmeetnetRestApi restApi) {
        this.httpClient = Objects.requireNonNull(httpClient);
        this.restApi = restApi;
    }

    public static LuchtmeetnetClient create(String url, Duration timeout) {
        LOG.info("Creating new REST client for URL '{}' with timeout {}", url, timeout);
        OkHttpClient httpClient = new OkHttpClient().newBuilder().connectTimeout(timeout).writeTimeout(timeout)
                .readTimeout(timeout).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient).build();
        ILuchtmeetnetRestApi restApi =  retrofit.create(ILuchtmeetnetRestApi.class);
        return new LuchtmeetnetClient(httpClient, restApi);
    }

    @Override
    public void close() {
        httpClient.dispatcher().executorService().shutdown();
        httpClient.connectionPool().evictAll();
    }

    /**
     * Retrieves all station metadata.
     * 
     * @return a list of station numbers
     * @throws IOException in case of a problem fetching the data
     */
    public List<StationsData> getStations() throws IOException {
        PagedResponseFetcher<StationsData> fetcher = new PagedResponseFetcher<>(10);
        return fetcher.fetch((page) -> restApi.getStations(page).execute().body());
    }

    public List<OrganisationData> getOrganisations() throws IOException {
        PagedResponseFetcher<OrganisationData> fetcher = new PagedResponseFetcher<>(10);
        return fetcher.fetch((page) -> restApi.getOrganisations(page).execute().body());
    }

    public List<ComponentsData> getComponents() throws IOException {
        PagedResponseFetcher<ComponentsData> fetcher = new PagedResponseFetcher<>(10);
        return fetcher.fetch((page) -> restApi.getComponents(page).execute().body());
    }

    /**
     * Retrieves the station data for a specific station.
     * 
     * @param number the station number
     * @return the station data, null if not found
     * @throws IOException in case of a problem fetching the data
     */
    public StationData getStationData(String number) throws IOException {
        Response<Station> response = restApi.getStation(number).execute();
        if (!response.isSuccessful()) {
            LOG.warn("Failed to retrieve station data for '{}': {}", number, response.errorBody().string());
            return null;
        }
        Station station = response.body();
        return station.getData();
    }

    public List<MeasurementData> getStationMeasurements(String number, String formula) throws IOException {
        PagedResponseFetcher<MeasurementData> fetcher = new PagedResponseFetcher<>(100);
        return fetcher.fetch((page) -> restApi.getStationMeasurements(number, page, formula).execute().body());
    }

    public List<MeasurementData> getMeasurements(String formula, Instant instant) throws IOException {
        Instant endTime = instant.truncatedTo(ChronoUnit.SECONDS);
        Instant startTime = endTime.minus(Duration.ofMinutes(70));
        PagedResponseFetcher<MeasurementData> fetcher = new PagedResponseFetcher<>(100);
        return fetcher.fetch((page) -> restApi.getMeasurements(page, formula, startTime, endTime).execute().body());
    }

    public List<MeasurementData> getLki(Instant instant) throws IOException {
        Instant endTime = instant.truncatedTo(ChronoUnit.SECONDS);
        Instant startTime = endTime.minus(Duration.ofMinutes(70));
        PagedResponseFetcher<MeasurementData> fetcher = new PagedResponseFetcher<>(10);
        return fetcher.fetch((page) -> restApi.getLki(page, startTime, endTime).execute().body());
    }

}
