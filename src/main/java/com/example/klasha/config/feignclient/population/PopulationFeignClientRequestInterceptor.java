package com.example.klasha.config.feignclient.population;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


@RequiredArgsConstructor
@Slf4j
public class PopulationFeignClientRequestInterceptor {

//    @Value("${vanso.username}")
//    String username;
//    @Value("${vanso.password}")
//    String password;
//
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            requestTemplate.header("Content-Type", "application/json");
//            requestTemplate.header("Accept", "application/json");
//        };
//    }
//    @Bean
//    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
//        return new BasicAuthRequestInterceptor(username, password);
//    }

}
