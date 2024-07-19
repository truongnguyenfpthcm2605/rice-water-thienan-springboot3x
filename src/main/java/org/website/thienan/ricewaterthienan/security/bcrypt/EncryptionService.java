package org.website.thienan.ricewaterthienan.security.bcrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.exceptions.ResourceExistingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EncryptionService {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5PADDING";
    private static final String HASH_ALGORITHM = "SHA-1";

    private SecretKeySpec secretKey;

    @Value("${spring.key.param.request}")
    private String mainKey;

    public EncryptionService() {
        setMainKey();
    }

    public void setMainKey() {
        try {
            MessageDigest sha = MessageDigest.getInstance(HASH_ALGORITHM);
            assert mainKey != null;
            byte[] key = mainKey.getBytes(StandardCharsets.UTF_8);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // Use only first 128 bit
            this.secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error main key : {}", e.getMessage());
            throw new ResourceExistingException("Main key valid");
        }
    }

    public String encrypt(String toEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(toEncrypt.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("Error encrypt : {}", e.getMessage());
            throw new ResourceExistingException("enCrypt Valid");
        }
    }

    public String decrypt(String toDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(toDecrypt);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Error decrypt : {}", e.getMessage());
            throw new ResourceExistingException("deCrypt Valid");
        }
    }

    public Boolean compareCrypt(String requestCrypt, String resourceCrypt) {
        return decrypt(requestCrypt).equals(resourceCrypt);
    }
}
