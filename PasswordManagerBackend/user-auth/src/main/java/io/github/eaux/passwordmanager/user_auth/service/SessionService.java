package io.github.eaux.passwordmanager.user_auth.service;

import io.github.eaux.passwordmanager.user_auth.models.Session;
import io.github.eaux.passwordmanager.user_auth.repository.SessionRepository;

public class SessionService {

    SessionRepository sessionRepository;

    public String getencryptedAESKeyByUserId(Long userId) {
        return sessionRepository.getencryptedAESKey(userId);
    }

    public Session createOrModifySession(Session newSession) {
        return sessionRepository.save(newSession);
    }

    public void deleteSessionBySessionId(Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }

}
