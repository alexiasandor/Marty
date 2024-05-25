package com.example.martybackend.email;

import com.example.martybackend.config.RabbitSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final RabbitSender rabbitSender;
    private final EmailSendingService emailSendingService;

    public NotificationService(RabbitSender rabbitSender, EmailSendingService emailSendingService) {
        this.rabbitSender = rabbitSender;
        this.emailSendingService = emailSendingService;
    }

    public void sendNotification(NotificationRequestDto requestDto) {
        // Trimite mesajul prin RabbitMQ
        rabbitSender.sendMessage(requestDto);

        // Trimite e-mailul stilizat
        emailSendingService.sendEmail(requestDto);
    }
}
