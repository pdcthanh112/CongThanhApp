package com.congthanh.notificationservice.service.strategy;

import com.congthanh.notificationservice.model.request.NotificationRequest;
import com.congthanh.notificationservice.service.email.EmailTemplates;
import com.congthanh.notificationservice.service.validator.EmailValidatorUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationStrategy implements NotificationStrategy {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public boolean send(NotificationRequest request) throws MessagingException {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setTo(request.getRecipient());
//            helper.setSubject(request.getSubject());
//            helper.setText(request.getContent(), true);
//
//            mailSender.send(message);
//            return true;
//        } catch (Exception e) {
//            log.error("Email sending failed", e);
//            return false;
//        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.setFrom("contact@aliboucoding.com");

        final String templateName = EmailTemplates.PAYMENT_CONFIRMATION.getTemplate();

        String destinationEmail = request.getAdditionalInfo().get("destinationEmail");
        String customerName = request.getAdditionalInfo().get("customerName");
        String amount = request.getAdditionalInfo().get("amount");
        String orderReference = request.getAdditionalInfo().get("orderReference");

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
            return true;
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
            return false;
        }
    }


    @Override
    public boolean validate(NotificationRequest request) {
        return request.getRecipient() != null &&
                EmailValidatorUtils.validateEmail(request.getRecipient());
    }
}