package io.github.eaux.passwordmanager.user_auth.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.eaux.passwordmanager.user_auth.dto.LoginDto;
import io.github.eaux.passwordmanager.user_auth.models.Credentials;
import io.github.eaux.passwordmanager.user_auth.models.RedisSessionData;
import io.github.eaux.passwordmanager.user_auth.models.Session;
import io.github.eaux.passwordmanager.user_auth.models.User;
import io.github.eaux.passwordmanager.user_auth.security.HashesUtil;
import io.github.eaux.passwordmanager.user_auth.security.TokenUtil;
import io.github.eaux.passwordmanager.user_auth.service.CredentialsService;
import io.github.eaux.passwordmanager.user_auth.service.RedisSessionService;
import io.github.eaux.passwordmanager.user_auth.service.SessionService;
import io.github.eaux.passwordmanager.user_auth.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    private RedisSessionService redisSessionService;

    private final WebClient webClient = WebClient.create("http://localhost:8080");

    HashesUtil hashesUtil = new HashesUtil();
    TokenUtil tokenUtil;

    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto loginDto) {

        Map<String, Object> responseBody = new HashMap<>();

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        if (!userService.doesUsernameExists(username)) {
            responseBody.put("message", "Username Does Not Exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        User currUser = userService.getUserByUserName(username);
        if (!password.equals(currUser.getPasswordHash())) {
            responseBody.put("message", "Wrong Password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }

        Session newSession = sessionService
                .createOrModifySession(new Session(0L, currUser.getUserId(), "", "", "", "", ""));

        tokenUtil = new TokenUtil(currUser.getUserId(), new Date(), new Date(), newSession.getSessionId());

        sessionService.createOrModifySession(new Session(newSession.getSessionId(), currUser.getUserId(),
                tokenUtil.generateToken(), "", newSession.getIssuedAt(), tokenUtil.getExpirationTime().toString(), ""));

        webClient.post().uri("/api/session/" + newSession.getSessionId().toString()).retrieve()
                .bodyToMono(Boolean.class).block();

        redisSessionService.saveSessionData(newSession.getSessionId(),
                new RedisSessionData(newSession.getUserId(), newSession.getEncryptedAESKey()));

        hashesUtil.setPrivateKey(credentialsService.getPrivateKey(currUser.getUserId()));

        responseBody.put("message", "Login Successfull");
        responseBody.put("publicKey", credentialsService.getPublicKey(currUser.getUserId()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseBody);
    }

    @PostMapping("/AESkey")
    public ResponseEntity<Map<String, Object>> getAESKeyAndToken(@RequestBody String AESKey) throws Exception {
        // Decode AESKey
        hashesUtil.setAESKey(AESKey);

        Session currSession = sessionService.getSessionBySessionId(tokenUtil.getSessionId());
        currSession.setEncryptedAESKey(AESKey);
        sessionService.createOrModifySession(currSession);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Key Received"));
    }

    @GetMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        Long sessionId = tokenUtil.getSessionId();
        sessionService.deleteSessionBySessionId(sessionId);
        redisSessionService.deleteSessionData(sessionId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Logout Successful"));
    }

    @GetMapping("/generateAndRefreshToken")
    public ResponseEntity<Map<String, Object>> generateAndRefreshToken() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Refresh Token", tokenUtil.refreshAndGenerateToken()));
    }

    public String generateToken() {
        return tokenUtil.generateToken();
    }

    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody LoginDto loginDto) throws NoSuchAlgorithmException {

        Map<String, Object> responseBody = new HashMap<>();

        String[] keys = hashesUtil.generateKeys();

        String publicKeyString = keys[0];
        String privateKeyString = keys[0];

        if (userService.doesUsernameExists(loginDto.getUsername())) {
            responseBody.put("message", "Username Already Exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        User newUser = userService.createUser(
                new User(0, loginDto.getUsername(), loginDto.getPassword(), new Date(), new Date(), new Date()));

        credentialsService.createCredentials(
                new Credentials(0, newUser.getUserId(), publicKeyString, privateKeyString, new Date(),
                        new Date()));

        Session newSession = sessionService
                .createOrModifySession(new Session(0L, newUser.getUserId(), "", "", "", "", ""));

        tokenUtil = new TokenUtil(newUser.getUserId(), new Date(), new Date(), newSession.getSessionId());

        sessionService.createOrModifySession(new Session(newSession.getSessionId(), newUser.getUserId(),
                tokenUtil.generateToken(), "", newSession.getIssuedAt(), tokenUtil.getExpirationTime().toString(), ""));

        webClient.post().uri("/api/session/" + newSession.getSessionId().toString()).retrieve()
                .bodyToMono(Boolean.class).block();

        redisSessionService.saveSessionData(newSession.getSessionId(),
                new RedisSessionData(newSession.getUserId(), newSession.getEncryptedAESKey()));

        responseBody.put("message", "User Registered");
        responseBody.put("publicKey", publicKeyString);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseBody);
    }

    @GetMapping("/generatePassword")
    public ResponseEntity<Map<String, Object>> generatePassword(@RequestBody int length,
            @RequestBody boolean includeSpecialChars,
            @RequestBody boolean includeNumbers) {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Generated Password", "abcd"));
    }

    // @PostMapping("/encryptData/{sessionId}")
    // public String encryptData(@PathVariable Long sessionId, @RequestBody String
    // responseString) throws Exception {
    // return hashesUtil.encryptResponse(responseString);
    // }

    // @PostMapping("/decryptData/{sessionId}")
    // public String decryptData(@PathVariable Long sessionId, @RequestBody String
    // requestString) throws Exception {
    // return hashesUtil.decryptpayload(requestString);
    // }
}