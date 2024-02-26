package com.example.demo.configuration;

import com.hazelcast.cache.HazelcastCachingProvider;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.cache.HazelcastCachingProvider;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class CacheConfig {
    @Bean
    public Config config() {
        Config conf=new Config().setInstanceName("hazelcast-instance").addMapConfig(
                new MapConfig().setName("users")
                        .setTimeToLiveSeconds(30)
        );
        //Hazelcast.newHazelcastInstance(conf);

        return conf;
    }
}
