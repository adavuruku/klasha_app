package com.example.klasha.api.models.thirdparty.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
public class PopulationFilterRequest {
    private String country;
}
