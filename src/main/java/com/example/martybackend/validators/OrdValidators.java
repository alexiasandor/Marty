package com.example.martybackend.validators;

import com.example.martybackend.dtos.OrdDto;
import com.example.martybackend.entity.User;
import com.example.martybackend.repository.OrdRepository;
import com.example.martybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.martybackend.constants.OrdConstants.ID_ERROR;
import static com.example.martybackend.constants.OrdConstants.TABEL_NR_ERROR;

@Component
public class OrdValidators {

    private static OrdRepository ordRepository;
    private static UserService userService;
    @Autowired
    public OrdValidators(OrdRepository ordRepository, UserService userService) {
        OrdValidators.ordRepository = ordRepository;
        OrdValidators.userService = userService;
    }
    public static String validateOrdDto(OrdDto ordDto) {
        StringBuilder errors = new StringBuilder();

        if (ordDto.getTableNr() <= 0) {
            errors.append(TABEL_NR_ERROR);
        }
//        if(ordDto.getUser().getUserId() <= 0){
//            errors.append(ID_ERROR);
//        }

       return errors.toString();
    }

    public static String isValidUser(User user) {
        StringBuilder errors = new StringBuilder();

        if(user.getUserId() <= 0){
            errors.append(ID_ERROR);
        }
        return errors.toString();
    }


    public static String validateIdDto( Long ordId) {
        StringBuilder errors = new StringBuilder();
        // id validation
        if (ordId <= 0) {
            errors.append(ID_ERROR);
        }

//        Optional<Ord> existingOrd = ordRepository.findById(ordId);
//        if (!existingOrd.isPresent()) {
//            errors.append(USER_DOES_NOT_EXIST);
//        }
        return errors.toString();
    }
}

