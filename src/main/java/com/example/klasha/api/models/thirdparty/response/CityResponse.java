package com.example.klasha.api.models.thirdparty.response;

import com.example.klasha.api.models.dto.CapitalDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CityResponse extends BaseResponse{
    private String[] data;
}
