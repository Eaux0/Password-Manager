package io.github.eaux.passwordmanager.user_auth.service;

import io.github.eaux.passwordmanager.user_auth.models.Credentials;
import io.github.eaux.passwordmanager.user_auth.repository.CredentialReprository;

public class CredentialsService {

    CredentialReprository credentialReprository;

    public String getPublicKey(Long userId) {
        return credentialReprository.getPublicKeyByUserId(userId);
    }

    public String getPrivatecKey(Long userId) {
        return credentialReprository.getPrivateKeyByUserId(userId);
    }

    public void deleteCredentials(Long userId) {
        Long credentialId = credentialReprository.getCredentialIdByUserId(userId);
        credentialReprository.deleteById(credentialId);
    }

    public Credentials createCredentials(Credentials newCredentials) {
        return credentialReprository.save(newCredentials);
    }

}
