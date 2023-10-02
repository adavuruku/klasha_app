package com.example.klasha.api;

import com.example.klasha.api.models.app.Response;
import com.example.klasha.api.models.dto.CountryDetailsRequestDto;
import com.example.klasha.services.PopulationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/klasha")
@RequiredArgsConstructor
@Slf4j
@Validated
public class Api {

    private final PopulationService populationService;
    public static final String SUCCESSFUL_RESPONSE_CODE = "200";
    private static final String SUCCESSFUL_MESSAGE = "Operation successfully performed";

    //1. Question 1
    @GetMapping("/cities/population")
    public ResponseEntity<Response> filterCitiesPopulation(@RequestParam("noOfCities") @Min(1) Long noOfCities) {
        Response response = new Response(SUCCESSFUL_RESPONSE_CODE, SUCCESSFUL_MESSAGE, null);
        response.setData(populationService.filterPopulation(noOfCities));
        return ResponseEntity.ok(response);
    }

    //2. Question 2
    @GetMapping("/country/details")
    public ResponseEntity<Response> countryDetails(@RequestParam("country") @NotBlank String country) {
        Response response = new Response(SUCCESSFUL_RESPONSE_CODE, SUCCESSFUL_MESSAGE, null);
        response.setData(populationService.getCountryDetails(country));
        return ResponseEntity.ok(response);
    }

    //3. Question 3
    @GetMapping("/cities")
    public ResponseEntity<Response> filterCitiesPopulation(@RequestParam("country") @NotBlank String country) {
        Response response = new Response(SUCCESSFUL_RESPONSE_CODE, SUCCESSFUL_MESSAGE, null);
        response.setData(populationService.getCountryState(country));
        return ResponseEntity.ok(response);
    }

    //4. Question 4
    @PostMapping("/currency/convert")
    public ResponseEntity<Response> filterCitiesPopulation(@Valid @RequestBody() CountryDetailsRequestDto countryDetailsRequestDto) {
        Response response = new Response(SUCCESSFUL_RESPONSE_CODE, SUCCESSFUL_MESSAGE, null);
        response.setData(populationService.convertCurrency(countryDetailsRequestDto));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cache/reset")
    public ResponseEntity<String> resetCache() {
        Response response = new Response(SUCCESSFUL_RESPONSE_CODE, SUCCESSFUL_MESSAGE, null);
        populationService.resyncCache();
        return ResponseEntity.ok("Completed");
    }
}
