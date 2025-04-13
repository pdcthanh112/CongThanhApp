package com.congthanh.orderservice.utils;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Helper {

  private static final SecureRandom random = new SecureRandom();
  private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public static String generateOrderNumber() {
    String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

    int sequencePart = 100 + random.nextInt(900);

    StringBuilder suffix = new StringBuilder();
    for (int i = 0; i < 9; i++) {
      suffix.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
    }

    return datePart + sequencePart + suffix.toString();
  }
}
