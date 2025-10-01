package io.github.eaux.passwordmanager.user_auth.dto;

import io.github.eaux.passwordmanager.user_auth.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    private Long userId;
    private String password;
    private String username;

    public User getUserFromLoginDto() {
        return new User(userId, username, password, new Date(), new Date(), new Date());
    }

}
