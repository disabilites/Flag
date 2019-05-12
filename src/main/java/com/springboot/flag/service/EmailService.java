package com.springboot.flag.service;

public interface EmailService {

    void sendVerifyEmail(String to, String code, String subject, String template);

    void sendRemindEmail(String to, String content);
}
