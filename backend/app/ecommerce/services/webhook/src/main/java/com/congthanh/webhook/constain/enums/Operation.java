package com.congthanh.webhook.constain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Operation {

    UPDATE("u"),
    CREATE("c"),
    DELETE("d"),
    READ("r");

    final String name;
}
