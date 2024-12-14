package nl.bertriksikken.luchtmeetnet.api.dto;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PaginationTest {

    private static final Logger LOG = LoggerFactory.getLogger(PaginationTest.class);

    /**
     * Verifies Pagination.toString()
     */
    @Test
    public void testToString() {
        Pagination pagination = new Pagination();
        LOG.info(pagination.toString());
    }

}
