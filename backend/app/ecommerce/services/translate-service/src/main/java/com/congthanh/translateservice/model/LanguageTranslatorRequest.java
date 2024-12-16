package com.congthanh.translateservice.model;

import lombok.Data;

@Data
public class LanguageTranslatorRequest {

    private String language;

    private String text;

}
