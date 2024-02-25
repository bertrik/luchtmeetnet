package nl.bertriksikken.luchtmeetnet.api;

import java.time.Instant;

import nl.bertriksikken.luchtmeetnet.api.dto.Components;
import nl.bertriksikken.luchtmeetnet.api.dto.Measurements;
import nl.bertriksikken.luchtmeetnet.api.dto.Organisations;
import nl.bertriksikken.luchtmeetnet.api.dto.Station;
import nl.bertriksikken.luchtmeetnet.api.dto.Stations;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * See https://api-docs.luchtmeetnet.nl/
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
