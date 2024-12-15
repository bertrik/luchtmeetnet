package nl.bertriksikken.luchtmeetnet;

import java.time.Instant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * See <a href="https://api-docs.luchtmeetnet.nl/">Luchtmeetnet API</a>
 */
public interface ILuchtmeetnetRestApi {

    @GET("/open_api/stations")
    Call<Stations> getStations(@Query("page") int page);

    @GET("/open_api/stations/{number}")
    Call<Station> getStation(@Path("number") String number);

    @GET("/open_api/stations/{number}/measurements")
    Call<Measurements> getStationMeasurements(@Path("number") String number, @Query("page") int page,
            @Query("formula") String formula);

    @GET("/open_api/organisations")
    Call<Organisations> getOrganisations(@Query("page") int page);

    @GET("/open_api/components")
    Call<Components> getComponents(@Query("page") int page);

    @GET("/open_api/measurements")
    Call<Measurements> getMeasurements(@Query("page") int page, @Query("formula") String formula,
            @Query("start") Instant startTime, @Query("end") Instant endTime);

    @GET("/open_api/lki")
    Call<Measurements> getLki(@Query("page") int page, @Query("start") Instant startTime,
            @Query("end") Instant endTime);

}
