package com.example.martybackend.email;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequestDto {
    private String emailId;
    private String recipient;
    private String subject;
    private String body;

}
