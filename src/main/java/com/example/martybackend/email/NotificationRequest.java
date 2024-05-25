package com.example.martybackend.email;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest {
    private String emailId;
    private String recipient;
    private String subject;
    private String body;
}
