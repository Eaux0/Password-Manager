package io.github.eaux.passwordmanager.user_auth.controller;

import java.util.Map;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.eaux.passwordmanager.user_auth.security.Hashes;
import io.github.eaux.passwordmanager.user_auth.security.TokenUtil;

@Controller
@RequestMapping("/api")
public class AuthController {

    Hashes hashes = new Hashes();
    TokenUtil tokenUtil = new TokenUtil();

    @SuppressWarnings("unused")
    @GetMapping("/login")
    public String login(@RequestParam Map<String, String> loginParamsMap) {

        String username = loginParamsMap.get("username");
        String password = loginParamsMap.get("password");
        Date issuedAt = new Date(Long.parseLong(loginParamsMap.get("issuedAt")));

        String hashedPassword = "hashedPassword";

        if (hashedPassword == null) {
            return "Login failed";
        }
        return hashedPassword.equals(password) ? "Login Successful" : "Login failed";
    }

    @PostMapping("/AESkey")
    public String[] getAESKeyAndJWT(@RequestParam String AESKey) throws Exception {
        hashes.setAESKey(AESKey);
        String publicKey = "publicKey";
        // Enter data in session table

        // sending public key and token
        String[] encryptionData = { hashes.encryptResponse(publicKey, AESKey),
                hashes.encryptResponse(generateJwtToken(), AESKey) };
        return encryptionData;
    }

    @GetMapping("/logout")
    public Boolean logout() {
        // End Session / delete from session table
        return true;
    }

    @GetMapping("/generateAndRefreshToken")
    public String generateAndRefreshToken() {
        return tokenUtil.refreshAndGenerateToken();
    }

    public String generateJwtToken() {
        return tokenUtil.generateToken();
    }

    @SuppressWarnings("unused")
    @PostMapping("/signUp")
    public String signUp(@RequestParam Map<String, String> signUpParamsMap) throws NoSuchAlgorithmException {

        String username = signUpParamsMap.get("username");
        String password = signUpParamsMap.get("password");

        String[] keys = hashes.generateKeys();

        String publicKeyString = keys[0];
        String privateKeyString = keys[0];

        // Store the public and private keys
        // Store the password and username

        String jwtToken = generateJwtToken();

        return "User registered successfully";
    }

    @GetMapping("/encryptData")
    public String encyptData(@RequestParam String responseString) throws Exception {
        // [ClassName, Class];
        return hashes.encryptResponse(responseString);
    }

    @PostMapping("/decryptData")
    public String decryptData(@RequestParam String requestString) throws Exception {
        return hashes.decryptpayload(requestString);
    }
}