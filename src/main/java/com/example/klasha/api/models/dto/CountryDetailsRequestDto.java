package com.example.klasha.api.models.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CountryDetailsRequestDto {
    @NotBlank
    private String country;

    @DecimalMin("1.0")
    private Double amount;

    @NotBlank
    private String targetCurrency;
}
