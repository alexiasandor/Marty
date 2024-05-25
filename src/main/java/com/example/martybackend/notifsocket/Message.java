package com.example.martybackend.notifsocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Message {
    private String content;
    private String sender;
    private Long recipientId;

}
