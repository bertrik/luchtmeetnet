package nl.bertriksikken.luchtmeetnet;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("ArrayRecordComponent")
public record Pagination(@JsonProperty("current_page") int currentPage,
                         @JsonProperty("next_page") int nextPage,
                         @JsonProperty("prev_page") int prevPage,
                         @JsonProperty("last_page") int lastPage,
                         @JsonProperty("first_page") int firstPage,
                         @JsonProperty("page_list") int[] pages) {

    public Pagination {
        pages = pages.clone();
    }

    @Override
    public int[] pages() {
        return pages.clone();
    }

    // package-private for testing
    Pagination() {
        this(0, 0, 0, 0, 0, new int[0]);
    }

}
