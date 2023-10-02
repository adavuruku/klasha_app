package com.example.klasha.api.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
public class CountryDetailsRequestDto {
    @NotBlank
    private String country;
    @Positive
    private Double amount;
    @NotBlank
    private String targetCurrency;
}
