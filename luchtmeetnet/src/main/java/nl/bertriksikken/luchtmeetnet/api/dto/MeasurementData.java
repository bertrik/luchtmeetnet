package nl.bertriksikken.luchtmeetnet.api.dto;

import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class MeasurementData {

    @JsonProperty("station_number")
    private String stationNumber;

    @JsonProperty("value")
    private double value;

    @JsonProperty("formula")
    private String formula;

    @JsonProperty("timestamp_measured")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date timeStamp;

    private MeasurementData() {
        // jackson constructor
    }

    public MeasurementData(String stationNumber, double value, String formula, Date timeStamp) {
        this();
        this.stationNumber = stationNumber;
        this.value = value;
        this.formula = formula;
        this.timeStamp = new Date(timeStamp.getTime());
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

    public Date getTimeStamp() {
        return new Date(timeStamp.getTime());
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{station=%s,value=%s,formula=%s,timestamp=%s}", stationNumber, value,
                formula, timeStamp);
    }

}
