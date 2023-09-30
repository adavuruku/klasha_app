package com.example.klasha.config.feignclient.population;

import com.example.klasha.api.models.app.SafeTokenResponse;
import com.example.klasha.exceptions.WebServiceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static feign.FeignException.errorStatus;

@Slf4j
public class PopulationFeignErrorDecoder implements ErrorDecoder {
  private final ObjectMapper objectMapper;

  public PopulationFeignErrorDecoder(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Exception decode(String methodKey, Response response) {
    try {
      String errorMessage = "Third-party service is currently unavailable. Please try again later.";
      if (response.body() == null)
        return new WebServiceException(errorMessage, HttpStatus.SERVICE_UNAVAILABLE);

      final InputStream inputStream = response.body().asInputStream();
      SafeTokenResponse baseResponse =  objectMapper.readValue(
          StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8), new TypeReference<>() {
          });

      return new WebServiceException(baseResponse == null ? errorMessage :
          baseResponse.getDescription(), HttpStatus.valueOf(response.status()), baseResponse);
    } catch (IOException exception) {
      log.error("Error reading input stream of feign: " + exception.getMessage(), exception);
      return errorStatus(methodKey, response);
    }
  }
}
