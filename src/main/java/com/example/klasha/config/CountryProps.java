package com.example.klasha.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "population")
@Getter
@Setter
public class CountryProps {
    private List<Object> countries; //the key name in xml cofig [population.countries]
}