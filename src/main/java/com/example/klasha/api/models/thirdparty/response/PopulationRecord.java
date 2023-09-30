package com.example.klasha.api.models.thirdparty.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class PopulationRecord {
    private String year;
    private String value;
    private String sex;
    private String reliabilty;
}
