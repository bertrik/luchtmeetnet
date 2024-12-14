package nl.bertriksikken.luchtmeetnet;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class PagedResponse<T> {

    @JsonProperty("pagination")
    private Pagination pagination;

    @JsonProperty("data")
    private List<T> data;

    public Pagination getPagination() {
        return pagination;
    }

    public List<T> getData() {
        return Collections.unmodifiableList(data);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{data=%s}", data);
    }

}
