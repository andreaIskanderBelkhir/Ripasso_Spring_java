package com.example.demo.configuration;

import com.hazelcast.cache.HazelcastCachingProvider;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.cache.HazelcastCachingProvider;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ObjectInputFilter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
public class HazelCastConfiguration {
    @Bean
    public static Config config() throws URISyntaxException {
        Config conf=new Config().setInstanceName("user").addMapConfig(
                new MapConfig().setName("com.example.demo.entity.User")
                        .setTimeToLiveSeconds(30)
        );
        //Hazelcast.newHazelcastInstance(conf);

        return conf;
    }
}
