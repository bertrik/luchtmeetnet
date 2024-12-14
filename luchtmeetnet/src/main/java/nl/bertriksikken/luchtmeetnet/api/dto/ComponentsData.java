package nl.bertriksikken.luchtmeetnet.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ComponentsData(@JsonProperty("formula") String formula,
                             @JsonProperty("name") MultiLingualText name) {

}
