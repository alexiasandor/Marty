package com.example.martybackend.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingService {

    private final JavaMailSender mailSender;


    public EmailSendingService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(NotificationRequestDto requestDto) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("sandor.alexia16@gmail.com");
            helper.setTo(requestDto.getRecipient());
            helper.setSubject(requestDto.getSubject());


            mailSender.send(message);
            System.out.println("Email sent successfully.");
        } catch (jakarta.mail.MessagingException e) {
            e.printStackTrace();
        }
    }
}
