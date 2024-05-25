package com.example.martybackend.dtos;

import com.example.martybackend.entity.Ord;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String firstNameUser;
    private String secondNameUser;
    private String role;
    private int age;
    private String emailUser;
    private String passwordUser;
    private List<Ord> ords;
}
