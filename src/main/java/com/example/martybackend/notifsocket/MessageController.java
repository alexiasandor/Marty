package com.example.martybackend.notifsocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        Long userId = message.getRecipientId();
        messagingTemplate.convertAndSendToUser(String.valueOf(userId), "/queue/messages", message);
    }
}
