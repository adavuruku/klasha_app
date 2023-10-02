package com.example.klasha.api;

import com.example.klasha.KlashaApplication;
import com.example.klasha.api.models.dto.CountryDetailsRequestDto;
import com.example.klasha.api.models.dto.MonetaryExchangeDto;
import com.example.klasha.services.PopulationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//https://stackoverflow.com/questions/51937581/jsonpathresultmatchers-cannot-be-applied-to-resultmatcher
@Slf4j
@WebAppConfiguration
@SpringBootTest(classes = KlashaApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@WebMvcTest(Api.class)
public class ApiTest {
    @InjectMocks
    PopulationService populationService;
    @Autowired
    private MockMvc mvc;

    @Test
    public void test_get_given_number_of_city_with_highest_population_in_country_is_successful()
            throws Exception {

        mvc.perform(get("/api/v1/klasha/cities/population?noOfCities=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.description").value("Operation successfully performed"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0]").isMap());

        mvc.perform(get("/api/v1/klasha/cities/population?noOfCities=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("The request is not valid."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isArray());

        mvc.perform(get("/api/v1/klasha/cities/population?noOfCities=")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("No value is passed for the required parameters."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());

        mvc.perform(get("/api/v1/klasha/cities/population?noOfCities='89'")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("We're unable to process your request at the moment. Please retry in a few minutes"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    public void test_get_detail_information_of_a_country_is_successful()
            throws Exception {

        mvc.perform(get("/api/v1/klasha/country/details?country=Nigeria")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Operation successfully performed"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.population").isNotEmpty())
                .andExpect(jsonPath("$.data.currency").isNotEmpty())
                .andExpect(jsonPath("$.data.capitalCity").isNotEmpty())
                .andExpect(jsonPath("$.data.iso2").isNotEmpty())
                .andExpect(jsonPath("$.data.iso3").isNotEmpty())
                .andExpect(jsonPath("$.data.location").isMap());

        mvc.perform(get("/api/v1/klasha/country/details?country=Mxx")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("No record was found for Mxx"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());

        mvc.perform(get("/api/v1/klasha/country/details?country=")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("The request is not valid."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    public void test_get_country_state_and_cities_information_successful()
            throws Exception {

        mvc.perform(get("/api/v1/klasha/cities?country=Ghana")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Operation successfully performed"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.country").value("Ghana"))
                .andExpect(jsonPath("$.data.stateCity").isArray())
                .andExpect(jsonPath("$.data.stateCity[0].state").isNotEmpty())
                .andExpect(jsonPath("$.data.stateCity[0].city").isArray())
                .andExpect(jsonPath("$.data.stateCity[0].city[0]").isNotEmpty());

        mvc.perform(get("/api/v1/klasha/cities?country=Mxx")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("No record was found for Mxx"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());

        mvc.perform(get("/api/v1/klasha/cities?country=")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("The request is not valid."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    public void test_convert_currency_successful()
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        //1. Valid and successful payload

        CountryDetailsRequestDto request1 = new CountryDetailsRequestDto();
        request1.setCountry("Nigeria");
        request1.setTargetCurrency("USD");
        request1.setAmount(100000.0);

        String payload = objectMapper.writeValueAsString(request1);

        mvc.perform( post("/api/v1/klasha/currency/convert")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Operation successfully performed"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.amount").exists())
                .andExpect(jsonPath("$.data.currency").value(request1.getTargetCurrency()));

        //2. Invalid value for country
        CountryDetailsRequestDto request2 = new CountryDetailsRequestDto();
        request2.setCountry("Mxx");
        request2.setTargetCurrency("USD");
        request2.setAmount(100000.0);
        String payload2 = objectMapper.writeValueAsString(request2);

        mvc.perform( post("/api/v1/klasha/currency/convert")
                        .content(payload2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("No record was found for Mxx"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());

        //3. Invalid value for target currency
        CountryDetailsRequestDto request3 = new CountryDetailsRequestDto();
        request3.setCountry("Nigeria");
        request3.setTargetCurrency("Nigeria");
        request3.setAmount(100000.0);
        String payload3 = objectMapper.writeValueAsString(request3);

        mvc.perform( post("/api/v1/klasha/currency/convert")
                        .content(payload3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("No record was found for [No exchange record found for conversion]"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());

        //4. Invalid value for amount
        CountryDetailsRequestDto request4 = new CountryDetailsRequestDto();
        request4.setCountry("Nigeria");
        request4.setTargetCurrency("USD");
        request4.setAmount(0.0);
        String payload4 = objectMapper.writeValueAsString(request4);


        mvc.perform( post("/api/v1/klasha/currency/convert")
                        .content(payload4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("The request is not well formatted."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].fieldName").isNotEmpty());

        //3. Invalid value for country
        CountryDetailsRequestDto request5 = new CountryDetailsRequestDto();
        request5.setCountry(null);
        request5.setTargetCurrency("USD");
        request5.setAmount(80.0);
        String payload5 = objectMapper.writeValueAsString(request5);


        mvc.perform( post("/api/v1/klasha/currency/convert")
                        .content(payload5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("The request is not well formatted."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].fieldName").isNotEmpty());
    }

    @Test
    public void test_get_sync_cache_successful()
            throws Exception {

        mvc.perform(get("/api/v1/klasha/cache/reset")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }
}
