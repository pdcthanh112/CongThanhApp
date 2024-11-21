package com.congthanh.project.service;

import com.congthanh.project.exception.ecommerce.EncryptionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@Component
public class SecurePaymentEncryptor {

    @Value("${payment.encryption.secret}")
    private String encryptionSecret;

    @Value("${payment.encryption.iv}")
    private String encryptionIV;

    public String encryptSensitiveData(String data) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = generateParameterSpec();

            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new EncryptionException("Encryption failed", e);
        }
    }

    public String decryptSensitiveData(String encryptedData) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = generateParameterSpec();

            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
            byte[] decryptedData = cipher.doFinal(decodedData);

            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new EncryptionException("Decryption failed", e);
        }
    }

    private Key generateKey() {
        return new SecretKeySpec(
                encryptionSecret.getBytes(StandardCharsets.UTF_8),
                "AES"
        );
    }

    private GCMParameterSpec generateParameterSpec() {
        byte[] iv = encryptionIV.getBytes(StandardCharsets.UTF_8);
        return new GCMParameterSpec(128, iv);
    }

}
