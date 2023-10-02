package com.example.klasha.api.models.thirdparty.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CityRequest {
    private String country;
    private String state;
}
