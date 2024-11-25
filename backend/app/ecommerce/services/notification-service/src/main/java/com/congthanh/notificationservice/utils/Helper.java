package com.congthanh.notificationservice.utils;

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
}
