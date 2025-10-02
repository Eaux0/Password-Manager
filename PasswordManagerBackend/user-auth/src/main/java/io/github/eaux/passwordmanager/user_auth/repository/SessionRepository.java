package io.github.eaux.passwordmanager.user_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.eaux.passwordmanager.user_auth.models.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("select s.encryptedAESKey from Session s where s.userId = :userId")
    public String getencryptedAESKey(Long userId);
}