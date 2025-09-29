package io.github.eaux.passwordmanager.user_auth.models;

import java.util.Date;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {

    long credentialId;
    long userId;
    String publicKey;
    String privateKey;
    Date createdAt;
    Date updatedAt;
}
