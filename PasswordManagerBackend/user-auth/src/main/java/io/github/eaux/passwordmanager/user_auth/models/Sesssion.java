package io.github.eaux.passwordmanager.user_auth.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Sesssion {

    long sessionId;
    long userId;
    String jwtToken;
    String encryptedAESKey;
    String issuedAt;
    String expiresAt;
    String lastActivity;

}
