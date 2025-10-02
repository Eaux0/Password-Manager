package io.github.eaux.passwordmanager.user_auth.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenUtil {

    private Long userId;
    private Date loginAt;
    private Date expirationTime;
    private Long sessionId;

    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
        if (loginAt != null) {
            this.expirationTime = new Date(loginAt.getTime() + 60 * 60 * 1000);
        }
    }

    public String generateToken() {
        Map<String, String> token = new HashMap<>();
        token.put("userId", userId.toString());
        token.put("loginAt", loginAt.toString());
        token.put("expirationTime", expirationTime.toString());
        token.put("sessionId", sessionId.toString());
        return token.toString();
    }

    public String refreshAndGenerateToken() {
        setExpirationTime(new Date(expirationTime.getTime() + 60 * 60 * 1000));
        return generateToken();
    }

}
