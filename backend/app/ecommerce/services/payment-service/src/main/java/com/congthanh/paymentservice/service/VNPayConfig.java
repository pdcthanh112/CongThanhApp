package com.congthanh.paymentservice.service;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class VNPayConfig {

    public static String hashAllFields(Map<String, String> fields, String hashSecret) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if (StringUtils.isNotBlank(fieldValue)) {
                try {
                    fieldValue = URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString());
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("Failed to encode field value", e);
                }
                sb.append(fieldName).append("=").append(fieldValue).append("&");
            }
        }
        sb.deleteCharAt(sb.length() - 1); // Remove last "&"

        return hmacSHA512(hashSecret, sb.toString());
    }

    private static String hmacSHA512(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HMACSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HMACSHA512");
            mac.init(secretKey);
            byte[] hmacData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            for (byte b : hmacData) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmacSHA512", e);
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader != null && !xForwardedForHeader.isEmpty()) {
            return xForwardedForHeader.split(",")[0].trim();
        } else {
            String remoteAddr = request.getRemoteAddr();
            if (remoteAddr == null) {
                remoteAddr = "127.0.0.1";
            }
            return remoteAddr;
        }
    }

    public static String getRandomNumber(int len) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
