package nl.bertriksikken.luchtmeetnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fetcher for luchtmeetnet paged response.
 *
 * @param <T> the type of response data
 */
final class PagedResponseFetcher<T> {

    private static final Logger LOG = LoggerFactory.getLogger(PagedResponseFetcher.class);

    private final int maxPage;

    /**
     * @param maxPage limit on the number of pages to retrieve
     */
    PagedResponseFetcher(int maxPage) {
        this.maxPage = maxPage;
    }

    /**
     * Fetches data of the specified type, collecting data from all pages.
     *
     * @param pageFetcher fetcher for one page of data
     * @return list of data from all pages
     */
    public List<T> fetch(IPageFetcher<T> pageFetcher) throws IOException {
        List<T> items = new ArrayList<>();
        for (int page = 1; page < maxPage; page++) {
            PagedResponse<T> response = pageFetcher.fetch(page);
            items.addAll(response.getData());
            Pagination pagination = response.getPagination();
            if (page >= pagination.lastPage()) {
                return items;
            }
            LOG.info("Fetched page {}/{}, getting next ...", pagination.currentPage(), pagination.lastPage());
        }
        LOG.warn("Limited number of pages to {}, return set truncated", maxPage);
        return items;
    }

    /**
     * Fetch one page of paginated data of type T.
     */
    public interface IPageFetcher<T> {
        PagedResponse<T> fetch(int page) throws IOException;
    }

}
