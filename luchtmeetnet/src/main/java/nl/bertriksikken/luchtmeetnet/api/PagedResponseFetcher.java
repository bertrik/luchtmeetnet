package nl.bertriksikken.luchtmeetnet.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.bertriksikken.luchtmeetnet.api.dto.PagedResponse;
import nl.bertriksikken.luchtmeetnet.api.dto.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class PagedResponseFetcher<T> {

    private static final Logger LOG = LoggerFactory.getLogger(PagedResponseFetcher.class);

    private final int maxPage;

    /**
     * @param maxPage limit on the number of pages to retrieve
     */
    PagedResponseFetcher(int maxPage) {
        this.maxPage = maxPage;
    }

    public List<T> fetch(IPageFetcher<T> pageFetcher) throws IOException {
        List<T> items = new ArrayList<>();
        for (int page = 1; page < maxPage; page++) {
            PagedResponse<T> response = pageFetcher.fetch(page);
            items.addAll(response.getData());
            Pagination pagination = response.getPagination();
            if (page >= pagination.getLastPage()) {
                return items;
            }
            LOG.info("Fetched page {}/{}, getting next ...", pagination.getCurrentPage(), pagination.getLastPage());
        }
        LOG.warn("Limited number of pages to {}, return set truncated", maxPage);
        return items;
    }

    public interface IPageFetcher<T> {
        PagedResponse<T> fetch(int page) throws IOException;
    }

}
