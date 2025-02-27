package org.cody.codyservice.config;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import redis.embedded.RedisServer;

@TestConfiguration
public class TestRedisConfiguration {
    
    private RedisServer redisServer;
    
    @PostConstruct
    public void startRedis() throws IOException {
        try {
            redisServer = new RedisServer(6379);
            redisServer.start();
        } catch (Exception e) {
            // 이미 Redis 서버가 실행 중인 경우
            System.out.println("Redis 서버가 이미 실행 중입니다: " + e.getMessage());
        }
    }
    
    @PreDestroy
    public void stopRedis() {
        if (redisServer != null && redisServer.isActive()) {
            redisServer.stop();
        }
    }
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }
} 