package com.example.martybackend.validators;


import com.example.martybackend.dtos.UserDto;
import com.example.martybackend.entity.User;
import com.example.martybackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.martybackend.constants.UserConstants.*;

@Component
public class UserValidators {
    private static UserRepository userRepository;

    @Autowired
    public UserValidators(UserRepository userRepository) {
        UserValidators.userRepository = userRepository;
    }
    public static String validateUserDto(UserDto userDto) {
        StringBuilder errors = new StringBuilder();

        // firstName validation
        if (Validator.isNullOrEmpty(userDto.getFirstNameUser())) {
            errors.append(FIRST_NAME_CAN_NOT_BE_EMPTY);
        }

        //  secondName validation
        if (Validator.isNullOrEmpty(userDto.getSecondNameUser())) {
            errors.append(SECOND_NAME_CAN_NOT_BE_EMPTY);
        }

        // role validation

        if (Validator.isNullOrEmpty(userDto.getRole())) {
            errors.append(ROLE_CAN_NOT_BE_EMPTY);
        }

        // age validation

         if (userDto.getAge() <= 0) {
             errors.append(AGE_MUST_BE_POSITIVE);
         }



        // email validation
        if (Validator.isNullOrEmpty(userDto.getEmailUser())) {
            errors.append(EMAIL_CAN_NOT_BE_EMPTY);
        } else if (!Validator.isValidEmail(userDto.getEmailUser())) {
            errors.append( INVALID_FORMAT);
        }

        // Password Validation
         if (Validator.isNullOrEmpty(userDto.getPasswordUser())) {
             errors.append(PASSWORD_CAN_NOT_BE_EMPTY);
         }

        return errors.toString();
    }

    public static String validateIdDto( Long userId) {
        StringBuilder errors = new StringBuilder();
        // id validation
        if (userId <= 0) {
            errors.append(ID_ERROR);
        }

        Optional<User> existingUser = userRepository.findById(userId);
        if (!existingUser.isPresent()) {
            errors.append(USER_ID_DOES_NOT_EXIST);
        }
        return errors.toString();
    }
}
