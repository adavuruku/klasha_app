package com.example.klasha.api.models.thirdparty.response;

import com.example.klasha.api.models.dto.CityRecordDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PopulationFilterResponse extends BaseResponse{
    private List<CityRecordDto> data;
}
