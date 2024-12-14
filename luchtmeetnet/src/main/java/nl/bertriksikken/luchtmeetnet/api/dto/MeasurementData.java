package nl.bertriksikken.luchtmeetnet.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class MeasurementData {

    @JsonProperty("station_number")
    private String stationNumber;

    @JsonProperty("value")
    private double value;

    @JsonProperty("formula")
    private String formula;

    @JsonProperty("timestamp_measured")
    private String timeStamp; // for example "2024-12-14T18:00:00+00:00"

    private MeasurementData() {
        // jackson constructor
    }

    public MeasurementData(String stationNumber, double value, String formula, String timeStamp) {
        this();
        this.stationNumber = stationNumber;
        this.value = value;
        this.formula = formula;
        this.timeStamp = timeStamp;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public double getValue() {
        return value;
    }

    public String getFormula() {
        return formula;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{station=%s,value=%s,formula=%s,timestamp=%s}", stationNumber, value,
                formula, timeStamp);
    }

}
