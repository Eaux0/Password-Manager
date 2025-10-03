package io.github.eaux.passwordmanager.user_auth.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.eaux.passwordmanager.user_auth.dto.LoginDto;
import io.github.eaux.passwordmanager.user_auth.models.Credentials;
import io.github.eaux.passwordmanager.user_auth.models.Session;
import io.github.eaux.passwordmanager.user_auth.models.User;
import io.github.eaux.passwordmanager.user_auth.security.HashesUtil;
import io.github.eaux.passwordmanager.user_auth.security.TokenUtil;
import io.github.eaux.passwordmanager.user_auth.service.CredentialsService;
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

    HashesUtil hashesUtil = new HashesUtil();
    TokenUtil tokenUtil;

    @GetMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        if (!userService.doesUsernameExists(username))
            return "Username Does Not Exists";

        User currUser = userService.getUserByUserName(username);
        if (!password.equals(currUser.getPasswordHash())) {
            return "Wrong Password";
        }

        Session newSession = sessionService
                .createOrModifySession(new Session(0L, currUser.getUserId(), "", "", "", "", ""));

        tokenUtil = new TokenUtil(currUser.getUserId(), new Date(), new Date(), newSession.getSessionId());

        sessionService.createOrModifySession(new Session(newSession.getSessionId(), currUser.getUserId(),
                tokenUtil.generateToken(), "", newSession.getIssuedAt(), tokenUtil.getExpirationTime().toString(), ""));

        hashesUtil.setPrivateKey(credentialsService.getPrivateKey(currUser.getUserId()));
        return "Login Successfull|" + credentialsService.getPublicKey(currUser.getUserId());

    }

    @PostMapping("/AESkey")
    public Boolean getAESKeyAndToken(@RequestBody String AESKey) throws Exception {
        // Decode AESKey
        hashesUtil.setAESKey(AESKey);

        Session currSession = sessionService.getSessionBySessionId(tokenUtil.getSessionId());
        currSession.setEncryptedAESKey(AESKey);
        sessionService.createOrModifySession(currSession);
        return true;
    }

    @GetMapping("/logout")
    public Boolean logout() {
        // End Session / delete from session table
        Long sessionId = tokenUtil.getSessionId();
        sessionService.deleteSessionBySessionId(sessionId);
        return true;
    }

    @GetMapping("/generateAndRefreshToken")
    public String generateAndRefreshToken() {
        return tokenUtil.refreshAndGenerateToken();
    }

    public String generateToken() {
        return tokenUtil.generateToken();
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody LoginDto loginDto) throws NoSuchAlgorithmException {

        String[] keys = hashesUtil.generateKeys();

        String publicKeyString = keys[0];
        String privateKeyString = keys[0];

        if (userService.doesUsernameExists(loginDto.getUsername()))
            return "Username Already Exists";

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

        return "User registered successfully";
    }

    @PostMapping("/encryptData")
    public String encryptData(@RequestBody String responseString) throws Exception {
        return hashesUtil.encryptResponse(responseString);
    }

    @PostMapping("/decryptData")
    public String decryptData(@RequestBody String requestString) throws Exception {
        return hashesUtil.decryptpayload(requestString);
    }
}