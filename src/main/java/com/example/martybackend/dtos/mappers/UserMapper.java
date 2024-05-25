package com.example.martybackend.dtos.mappers;

import com.example.martybackend.dtos.UserDto;
import com.example.martybackend.entity.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserMapper {
    public static User fromDto(UserDto userDto){
        return User.builder()
                .userId(userDto.getUserId())
                .firstNameUser(userDto.getFirstNameUser())
                .secondNameUser(userDto.getSecondNameUser())
                .role(userDto.getRole()).age(userDto.getAge())
                .emailUser(userDto.getEmailUser())
                .passwordUser(userDto.getPasswordUser())
                .ords(userDto.getOrds())
                .build();
    }

    public static UserDto toEntity(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .firstNameUser(user.getFirstNameUser())
                .secondNameUser(user.getSecondNameUser())
                .role(user.getRole()).age(user.getAge())
                .emailUser(user.getEmailUser())
                .passwordUser(user.getPasswordUser())
                .ords(user.getOrds())
                .build();
    }
}
