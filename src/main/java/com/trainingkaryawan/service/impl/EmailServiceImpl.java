package com.trainingkaryawan.service.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
public class EmailServiceImpl  {
    @Value("${spring.mail.host}")
    public String mailHost;
    @Value("${spring.mail.port}")
    public int mailPort;
    @Value("${spring.mail.username}")
    public String mailUsername;
    @Value("${spring.mail.password}")
    public String mailPassword;

    public void sendEmail(String to, String subject, String text) {
        log.info("Start sending email with to {}", to);
        boolean html = true;
        try {
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                mailSender.setHost(mailHost);
                mailSender.setPort(mailPort);
                mailSender.setUsername(mailUsername);
                mailSender.setPassword(mailPassword);

                Properties properties = new Properties();
                properties.setProperty("mail.smtp.auth", String.valueOf(Boolean.TRUE));
                properties.setProperty("mail.smtp.starttls.enable", String.valueOf(Boolean.TRUE));

                mailSender.setJavaMailProperties(properties);
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                helper.setFrom(mailUsername);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(text, html);

                mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error sending email : ", e);
        }
    }

}
