package nl.bertriksikken.luchtmeetnet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Locale;

public abstract class PagedResponse<T> {

    @JsonProperty("pagination")
    private Pagination pagination;

    @JsonProperty("data")
    private List<T> data;

    public Pagination getPagination() {
        return pagination;
    }

    public List<T> getData() {
        return List.copyOf(data);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{data=%s}", data);
    }

}
