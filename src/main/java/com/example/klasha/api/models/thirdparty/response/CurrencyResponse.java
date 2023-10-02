package com.example.klasha.api.models.thirdparty.response;

import com.example.klasha.api.models.dto.CapitalDto;
import com.example.klasha.api.models.dto.CurrencyDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CurrencyResponse extends BaseResponse{
    private CurrencyDto data;
}
