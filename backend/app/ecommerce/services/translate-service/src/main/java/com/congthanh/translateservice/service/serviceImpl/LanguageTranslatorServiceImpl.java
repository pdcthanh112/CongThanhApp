package com.congthanh.translateservice.service.serviceImpl;

import com.congthanh.translateservice.model.LanguageTranslatorRequest;
import com.congthanh.translateservice.service.LanguageTranslatorService;
import com.congthanh.translateservice.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageTranslatorServiceImpl implements LanguageTranslatorService {

    private final OpenAiService openAiService;

    @Override
    public String translate(LanguageTranslatorRequest request){
        return openAiService.translate(request.getText(), request.getLanguage());
    }

}
