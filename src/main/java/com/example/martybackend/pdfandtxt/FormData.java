package com.example.martybackend.pdfandtxt;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class FormData {
    private String fileType;
    private String firstName;
    private String lastName;
    private int invoiceValue;
    private String additionalInfo;

}