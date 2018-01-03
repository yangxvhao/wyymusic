package com.yangxvhao.proxy.until;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangxvhao
 * @date 18-1-3.
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

    @Bean(name = "redisProperties")
    @ConditionalOnMissingBean
    public RedisProperties redisProperties(){
        return new RedisProperties();
    }

    @Autowired
    @Qualifier("redisProperties")
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient(){

        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
        singleServerConfig.setPassword(redisProperties.getPassword());
        singleServerConfig.setDatabase(redisProperties.getDatabase());
        log.info("redis properties:{}", singleServerConfig.getAddress());
        return Redisson.create(config);
    }
}
