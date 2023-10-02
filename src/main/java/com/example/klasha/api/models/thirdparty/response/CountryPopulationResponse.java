package com.example.klasha.api.models.thirdparty.response;

import com.example.klasha.api.models.dto.CountryPopulationDetailDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CountryPopulationResponse extends BaseResponse{
    private CountryPopulationDetailDto data;
}
