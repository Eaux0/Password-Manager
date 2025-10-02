package io.github.eaux.passwordmanager.user_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.eaux.passwordmanager.user_auth.models.Credentials;

public interface CredentialReprository extends JpaRepository<Credentials, Long> {

    @Query("select c.publicKey from Credentials c where c.userId = :userId")
    public String getPublicKeyByUserId(long userId);

    @Query("select c.privateKey from Credentials c where c.userId = :userId")
    public String getPrivateKeyByUserId(long userId);

    @Query("select c.credentialId from Credentials c where c.userId = :userId")
    public Long getCredentialIdByUserId(long userId);

}
