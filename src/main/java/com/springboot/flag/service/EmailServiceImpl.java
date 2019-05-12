package com.springboot.flag.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendVerifyEmail(String to, String code, String subject, String template) {
        Context context = new Context();
        context.setVariable("code", code);
        String emailContent = templateEngine.process(template, context);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom("yourEmailAddr");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("subject");
            mimeMessageHelper.setText(emailContent, true);
            javaMailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendRemindEmail(String to, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom("yourEmailAddr");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("subject");
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
