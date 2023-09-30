package com.example.klasha.api.clients;

import com.example.klasha.api.clients.feign.PopulationFeignClient;
import com.example.klasha.api.models.thirdparty.request.PopulationFilterRequest;
import com.example.klasha.api.models.thirdparty.response.PopulationFilterResponse;
import com.example.klasha.exceptions.BadRequestException;
import com.example.klasha.exceptions.NotFoundException;
import com.example.klasha.exceptions.RemoteServerException;
import com.example.klasha.exceptions.WebServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Slf4j
public class PopulationClientFeignt {

    private final PopulationFeignClient populationFeignClient;

    public Optional<PopulationFilterResponse> filterPopulation(PopulationFilterRequest populationFilterRequest) throws JsonProcessingException {
        PopulationFilterResponse response = null;
        try {
            log.info("population filter request body {} ", new ObjectMapper().writeValueAsString(populationFilterRequest));
            response = populationFeignClient.filterPopulationByCountry(populationFilterRequest);
            log.info("population response body {} ", new ObjectMapper().writeValueAsString(response));

        } catch (FeignException e) {
            log.error(e.getMessage());
            if (e.status() == HttpStatus.NOT_FOUND.value()){
                throw new NotFoundException(e.getMessage());
            }
            if (e.status() == HttpStatus.BAD_REQUEST.value() || e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                throw new BadRequestException(e.getMessage(), null, HttpStatus.BAD_REQUEST);
            }
        } catch (WebServiceException exception) {
            if (Objects.nonNull(exception.getResponse())) {
                log.error(exception.getMessage());
                response = (PopulationFilterResponse) exception.getResponse();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (isNull(response)) {
            throw new RemoteServerException();
        }
        return Optional.ofNullable(response);
    }
}
