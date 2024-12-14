package nl.bertriksikken.luchtmeetnet.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bertriksikken.luchtmeetnet.Measurements;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class MeasurementDataTest {

    private static final Logger LOG = LoggerFactory.getLogger(MeasurementDataTest.class);

    private static final ZoneId ZONE = ZoneId.of("Europe/Amsterdam");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZONE);

    @Test
    public void testSerialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("Europe/Amsterdam"));
        String timeStamp = FORMATTER.format(now.truncatedTo(ChronoUnit.SECONDS));
        Measurements.Data data = new Measurements.Data("123", 1.23, "NO", timeStamp);
        String s = mapper.writeValueAsString(data);
        LOG.info(s);
    }

}
