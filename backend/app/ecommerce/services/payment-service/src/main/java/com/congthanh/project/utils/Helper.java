package com.congthanh.project.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Helper {

    public String generateSlug(String productName) {
        String normalizedString = Normalizer.normalize(productName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String slug = pattern.matcher(normalizedString)
                .replaceAll("")
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-zA-Z0-9-]", "")
                .toLowerCase();

        return slug;
    }

    public static String getBaseURl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(scheme).append("://").append(serverName);
        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        if (url.toString().endsWith("/")) {
            url.append("/");
        }
        return url.toString();
    }
}
