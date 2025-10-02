package io.github.eaux.passwordmanager.user_auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.eaux.passwordmanager.user_auth.models.Session;
import io.github.eaux.passwordmanager.user_auth.repository.SessionRepository;

@Service
public class SessionService {

    @Autowired
    SessionRepository sessionRepository;

    public String getencryptedAESKeyByUserId(Long userId) {
        return sessionRepository.getencryptedAESKey(userId);
    }

    public Session getSessionBySessionId(Long sessionId) {
        return sessionRepository.findById(sessionId).get();
    }

    public Session createOrModifySession(Session newSession) {
        return sessionRepository.save(newSession);
    }

    public void deleteSessionBySessionId(Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }

}
