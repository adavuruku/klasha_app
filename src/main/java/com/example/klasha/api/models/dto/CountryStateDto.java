package com.example.klasha.api.models.dto;

import com.example.klasha.api.models.thirdparty.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CountryStateDto {
    private String name;
    private String iso3;
    private String iso2;
    private List<StateDto> states;
}
