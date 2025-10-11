// src/main/java/io/github/eaux/passwordmanager/config/RedisConfig.java
package io.github.eaux.passwordmanager.user_auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import io.github.eaux.passwordmanager.user_auth.models.RedisSessionData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, RedisSessionData> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, RedisSessionData> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Key: plain string
        template.setKeySerializer(new StringRedisSerializer());

        // Value: Jackson serializer
        Jackson2JsonRedisSerializer<RedisSessionData> valueSerializer = new Jackson2JsonRedisSerializer<>(
                RedisSessionData.class);

        // Configure ObjectMapper to support default typing if needed
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build(),
                ObjectMapper.DefaultTyping.NON_FINAL);
        valueSerializer.setObjectMapper(objectMapper);

        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}
