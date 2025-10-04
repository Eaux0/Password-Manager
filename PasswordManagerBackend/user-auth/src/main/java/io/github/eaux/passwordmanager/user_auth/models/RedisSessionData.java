package io.github.eaux.passwordmanager.user_auth.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RedisSessionData implements Serializable {
    private Long userId;
    private String aesKey;
}
