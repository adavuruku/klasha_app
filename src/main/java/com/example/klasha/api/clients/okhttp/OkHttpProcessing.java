package com.example.klasha.api.clients.okhttp;

import com.example.klasha.exceptions.RemoteServerException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class OkHttpProcessing {
    @Value("${population.base_url}")
    private String population_url;

    private final OkHttpClient client;

    public OkHttpProcessing(OkHttpClient OkHttpClient){
        this.client = OkHttpClient;
    }

    @Async
    public CompletableFuture<String> post(String payload, String path){
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, payload);
        Request request = new Request.Builder()
                .url(population_url + path)
                .method("POST", body)
                .build();
        OkHttpResponseFuture callback = new OkHttpResponseFuture();
        client.newCall(request).enqueue(callback);
//        try {
//            log.info("Started processing for thread {}", Thread.currentThread().getName());
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return callback.future.thenApply(response -> {
            try {
                return response.body().string();
            } catch (Exception e) {
                throw new RemoteServerException();
            } finally {
                response.close();
            }
        });
    }
}
