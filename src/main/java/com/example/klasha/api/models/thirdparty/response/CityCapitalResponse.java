package com.example.klasha.api.models.thirdparty.response;

import com.example.klasha.api.models.dto.CapitalDto;
import com.example.klasha.api.models.dto.CountryPopulationDetailDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CityCapitalResponse extends BaseResponse{
    private CapitalDto data;
}
