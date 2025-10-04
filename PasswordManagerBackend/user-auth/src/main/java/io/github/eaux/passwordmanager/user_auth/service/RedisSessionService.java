package io.github.eaux.passwordmanager.user_auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.github.eaux.passwordmanager.user_auth.models.RedisSessionData;

import java.time.Duration;

@Service
public class RedisSessionService {

    private static final String PREFIX = "session:";

    @Autowired
    private RedisTemplate<String, RedisSessionData> redisTemplate;

    public void saveSessionData(Long sessionId, RedisSessionData data) {
        redisTemplate.opsForValue().set(PREFIX + sessionId, data, Duration.ofHours(1));
    }

    public RedisSessionData getSessionData(Long sessionId) {
        return redisTemplate.opsForValue().get(PREFIX + sessionId);
    }

    public String getAESKeyStringForSessionId(Long sessionId) {
        return getSessionData(sessionId).getAesKey();
    }

    public Long getUserIdForSessionId(Long sessionId) {
        return getSessionData(sessionId).getUserId();
    }

    public void deleteSessionData(Long sessionId) {
        redisTemplate.delete(PREFIX + sessionId);
    }
}