package io.github.eaux.passwordmanager.user_auth.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    long userId;
    String username;
    String passwordHash;
    Date createdAt;
    Date updatedAt;
    Date lastLogin;

}
