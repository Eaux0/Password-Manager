package io.github.eaux.passwordmanager.user_auth.security;

import java.util.Base64;
import javax.crypto.Cipher;
import java.security.*;
import javax.crypto.spec.SecretKeySpec;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class HashesUtil {

    private String AESKey;
    private KeyPair keyPair;
    private String privateKey;

    private SecretKeySpec getKey(String hasher) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(hasher.getBytes("UTF-8"));
        return new SecretKeySpec(key, 0, 16, "AES");
    }

    public String encryptResponse(String plainText) throws Exception {
        return encryptResponse(plainText, AESKey);
    }

    public String encryptResponse(String plainText, String hasher) throws Exception {
        SecretKeySpec secretKey = getKey(hasher);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decryptpayload(String encryptedBase64) throws Exception {
        return decryptpayload(encryptedBase64, AESKey);
    }

    public String decryptpayload(String encryptedBase64, String hasher) throws Exception {
        SecretKeySpec secretKey = getKey(hasher);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedBase64));
        return new String(decryptedBytes, "UTF-8");
    }

    public String decryptWithPrivateKey(PrivateKey privateKey, String base64Encrypted) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(base64Encrypted);
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    public String[] generateKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        String[] keys = { publicKeyString, privateKeyString };
        return keys;

    }

}
