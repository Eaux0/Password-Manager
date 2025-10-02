package io.github.eaux.passwordmanager.user_auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.eaux.passwordmanager.user_auth.models.Credentials;
import io.github.eaux.passwordmanager.user_auth.repository.CredentialReprository;

@Service
public class CredentialsService {

    @Autowired
    CredentialReprository credentialReprository;

    public String getPublicKey(Long userId) {
        return credentialReprository.getPublicKeyByUserId(userId);
    }

    public String getPrivateKey(Long userId) {
        return credentialReprository.getPrivateKeyByUserId(userId);
    }

    public void deleteCredentialsByUserId(Long userId) {
        Long credentialId = credentialReprository.getCredentialIdByUserId(userId);
        credentialReprository.deleteById(credentialId);
    }

    public void deleteCredentialsById(Long sessionId) {
        credentialReprository.deleteById(sessionId);
    }

    public Credentials createCredentials(Credentials newCredentials) {
        return credentialReprository.save(newCredentials);
    }

}
