package com.example.klasha.api.clients.okhttp;

import com.example.klasha.api.models.thirdparty.response.CityRecord;
import com.example.klasha.api.models.thirdparty.response.PopulationFilterResponse;
import com.example.klasha.exceptions.RemoteServerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AsyncProcessing {
    public static <T> CompletableFuture<List<T>> join(List<CompletableFuture<T>> futures) {
        CompletableFuture[] cfs = futures.toArray(new CompletableFuture[futures.size()]);

        return CompletableFuture.allOf(cfs)
                .thenApply(v -> combineIndividualResults(cfs));
    }

    public static CompletableFuture<List<CityRecord>> combineIndividualResults(CompletableFuture[] cfs){
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
}
