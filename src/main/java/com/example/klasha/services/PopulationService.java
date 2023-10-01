package com.example.klasha.services;

import com.example.klasha.api.clients.okhttp.OkHttpResponseFuture;
import com.example.klasha.api.models.thirdparty.request.PopulationFilterRequest;
import com.example.klasha.api.models.thirdparty.response.CityRecord;
import com.example.klasha.api.models.thirdparty.response.PopulationFilterResponse;
import com.example.klasha.api.models.thirdparty.response.TestResponse;
import com.example.klasha.config.CountryProps;
import com.example.klasha.exceptions.RemoteServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Slf4j
public class PopulationService {
    private final CountryProps countryProps;



    public PopulationService(CountryProps countryProps) {
        this.countryProps = countryProps;
    }
    public void testOkhttp(){
        try {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, "{\n\t\"limit\": 10,\n\t\"order\": \"asc\",\n\t\"orderBy\": \"name\",\n\t\"country\": \"nigeria\"\n}");
        Request request = new Request.Builder()
                .url("https://countriesnow.space/api/v0.1/countries/population/cities/filter")
                .method("POST", body)
                .build();

            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
//            log.info("population filter request body {} ", new ObjectMapper().writeValueAsString(response.body()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<List<CityRecord>> testCompletable(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, "{\n\t\"limit\": 10,\n\t\"order\": \"asc\",\n\t\"orderBy\": \"name\",\n\t\"country\": \"nigeria\"\n}");
        Request request = new Request.Builder()
                .url("https://countriesnow.space/api/v0.1/countries/population/cities/filter")
                .method("POST", body)
                .build();
        OkHttpResponseFuture callback = new OkHttpResponseFuture();
        client.newCall(request).enqueue(callback);

        return callback.future.thenApply(response -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                PopulationFilterResponse filterRequest = objectMapper.readValue(response.body().string(), PopulationFilterResponse.class);
                return filterRequest.getData();
            } catch (IOException e) {
                throw new RemoteServerException();
            } finally {
                response.close();
            }
        });
    }

    public List<CityRecord> manyRecord(){

        try {
            CompletableFuture<List<CityRecord>> c1 = this.testCompletable();
            CompletableFuture<List<CityRecord>> c2 = this.testCompletable();
            CompletableFuture<List<CityRecord>> c3 = this.testCompletable();

            CompletableFuture.allOf(c1,c2,c3).join();

            List<CityRecord> allRecord = new ArrayList<>();
            allRecord.addAll(c1.get());
            allRecord.addAll(c2.get());
            allRecord.addAll(c3.get());
            return allRecord;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

}
