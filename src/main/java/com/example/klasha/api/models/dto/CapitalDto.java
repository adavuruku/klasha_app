package com.example.klasha.api.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CapitalDto {
    private String name;
    private String capital;
    private String iso2;
    private String iso3;
}
