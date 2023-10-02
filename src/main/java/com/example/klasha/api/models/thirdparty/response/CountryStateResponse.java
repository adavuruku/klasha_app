package com.example.klasha.api.models.thirdparty.response;

import com.example.klasha.api.models.dto.CountryStateDto;
import com.example.klasha.api.models.dto.StateDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CountryStateResponse extends BaseResponse{
    private CountryStateDto data;
}
