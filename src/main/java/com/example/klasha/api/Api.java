package com.example.klasha.api;

import com.example.klasha.api.models.app.Response;
import com.example.klasha.services.PopulationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/population")
@RequiredArgsConstructor
@Slf4j
public class Api {

    private final PopulationService populationService;
    public static final String SUCCESSFUL_RESPONSE_CODE = "200";
    private static final String SUCCESSFUL_MESSAGE = "Operation successfully performed";
    @GetMapping("/cities/{noOfCities}")
    public ResponseEntity<Response> activateConsumer(@PathVariable Long noOfCities) {
        Response response = new Response(SUCCESSFUL_RESPONSE_CODE, SUCCESSFUL_MESSAGE, null);
        response.setData(populationService.processPopulationFilter(noOfCities));
        return ResponseEntity.ok(response);
    }

}
