package com.example.klasha.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
public class CaffeineCacheConfig {
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caffeineCaches = new ArrayList<>();
    //    for (CacheConstant cacheType : CacheConstant.values()) {
            caffeineCaches.add(new CaffeineCache("monetary_exchange_cache",
                    Caffeine.newBuilder()
    //                        .expireAfterWrite(cacheType.getExpires(), TimeUnit.SECONDS)
                            .maximumSize(500)
                            .build()));
    //    }
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;
    }
}

