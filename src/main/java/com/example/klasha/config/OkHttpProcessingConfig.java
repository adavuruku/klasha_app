package com.example.klasha.config;

import com.example.klasha.api.clients.okhttp.OkHttpProcessing;
import com.example.klasha.services.PopulationService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class OkHttpProcessingConfig {
    @Bean
    public OkHttpProcessing createOkHttp(OkHttpClient okHttpClient) {
        return new OkHttpProcessing(okHttpClient);
    }

    @Bean
    public OkHttpClient createClient(){
        return new OkHttpClient().newBuilder()
                .build();
    }
}
