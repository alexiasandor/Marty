package com.example.martybackend.controller;

import com.example.martybackend.dtos.UserDto;
import com.example.martybackend.repository.UserRepository;
import com.example.martybackend.service.UserService;
import com.example.martybackend.validators.UserValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.example.martybackend.constants.SaleConstants.INVALID_INPUT_FIELDS;
import static com.example.martybackend.constants.SaleConstants.START_PROCESSING;
import static com.example.martybackend.constants.UserConstants.*;

@Controller
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method returns all users from database.
     * @return a list of users
     */
    @GetMapping("/getAllUsers")
    public ModelAndView getUser() {
        ModelAndView modelAndView = new ModelAndView("getAllUsers");
        try{
            LOGGER.info(START_PROCESSING);
            List<UserDto> users = userService.findUser();
           LOGGER.info("Users:" + users);
            modelAndView.addObject("users", users);
        return modelAndView;}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }

    /**
     * This method returns only one user.
     *  @param userId the user's id which we use to search
     *   @return the user which has id = userId
     */
    @PostMapping("/getUserById/{id}")
    public ModelAndView getUserById(@RequestParam("id") Long userId) {
        ModelAndView modelAndView = new ModelAndView("getAllUsers");
        try{
            LOGGER.info(START_PROCESSING);
            String validationErrors = UserValidators.validateIdDto(userId);

            if (!validationErrors.isEmpty()) {
                modelAndView.addObject("errors", validationErrors);
                return modelAndView;
            }

            UserDto user = userService.findUserById(userId);
            LOGGER.info(USER_INSERTED_SUCCESSFULLY + user.getUserId()+ " " + user.getFirstNameUser());
            modelAndView.addObject("user", user);
        return modelAndView;}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }

    /**
     * This method creates a user
     * @param userDto the user which has dto type
     * @return return success messages or error messages
     */
    @PostMapping("/do")
    public ModelAndView insert(@Validated UserDto userDto, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("addUser");
        try{
            LOGGER.info(START_PROCESSING);
            String validationErrors = UserValidators.validateUserDto(userDto);

            if (!validationErrors.isEmpty()) {

                modelAndView.addObject("errors", validationErrors);
                modelAndView.addObject("userDto", userDto);
                return modelAndView;
            }

            userService.insert(userDto);
            LOGGER.info(USER_INSERTED_SUCCESSFULLY + userDto.getUserId());
            redirectAttributes.addFlashAttribute("success", USER_INSERTED_SUCCESSFULLY);

            return new ModelAndView("redirect:/addUser");
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/addUser");
        }
    }


    /**
     This method updates a user
     * @param userDto the user with the updated information
     * @return returns success message and updated user
     */
    @PostMapping("/updateUser")
    public ModelAndView updateUser(@Validated @ModelAttribute("userDto") UserDto userDto, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("updateUser");
        try{
            LOGGER.info(START_PROCESSING );
        Long userId = userDto.getUserId(); // Utilizăm ID-ul din obiectul UserDto

        String validationErrors = UserValidators.validateUserDto(userDto);
        String validationErrors1 = UserValidators.validateIdDto(userId);

        if (!validationErrors.isEmpty() || !validationErrors1.isEmpty()) {
            LOGGER.info(INVALID_INPUT_FIELDS);
            modelAndView.addObject("errors", validationErrors);
            modelAndView.addObject("errors", validationErrors1);
            modelAndView.addObject("userDto", userDto);
            modelAndView.addObject("userId", userId);
            return modelAndView;
        }

        userService.updateUser(userDto);
        redirectAttributes.addFlashAttribute("success", USER_UPDATED_SUCCESSFULLY);
        LOGGER.info(USER_UPDATED_SUCCESSFULLY);
        return new ModelAndView("redirect:/updateUser");
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/updateUser");
        }
    }


    /**
     * This method deletes a user
     * @param userId user's id we want to delete
     * @return success message
     */
    @PostMapping("/deleteUser/{id}")
    public ModelAndView deleteUser(@RequestParam("id") Long userId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("deleteUser");
        try {
            LOGGER.info(START_PROCESSING);
            String validationErrors = UserValidators.validateIdDto(userId);
            if (!validationErrors.isEmpty()) {
                LOGGER.info(INVALID_INPUT_FIELDS);
                modelAndView.addObject("errors", validationErrors);
                return modelAndView;
            }
            userService.deleteUser(userId);
            redirectAttributes.addFlashAttribute("success", USER_DELETED_SUCCESSFULLY);
            LOGGER.info(USER_DELETED_SUCCESSFULLY);
            return new ModelAndView("redirect:/deleteUser");
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/deleteSale");
        }
    }

    /**
     * This method returns all users which are waiters
     * @return a list of waiters
     */
    @GetMapping("/getAllWaiters")
    public ModelAndView getAllWaiters() {
        ModelAndView modelAndView = new ModelAndView("viewUserActivity");
        try{
            LOGGER.info(START_PROCESSING);
        List<UserDto> chelner= userService.findWaiters();
        modelAndView.addObject("chelner", chelner);
        LOGGER.info(WAITER_FOUND);
        return modelAndView;}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/viewUserActivity");
        }
    }
    /**
     * This method returns only one user.
     *  @param userId the user's id which we use to search
     *   @return the user which has id = userId
     */
    @PostMapping("/getUsrById/{id}")
    public ModelAndView getUserById2(@RequestParam("id") Long userId) {
        ModelAndView modelAndView = new ModelAndView("happyDay");
        try{
            LOGGER.info(START_PROCESSING);
            String validationErrors = UserValidators.validateIdDto(userId);

            if (!validationErrors.isEmpty()) {
                modelAndView.addObject("errors", validationErrors);
                return modelAndView;
            }

            UserDto user = userService.findUserById(userId);
            if(user==null){
                LOGGER.info(USER_NOT_FOUND);
                modelAndView.addObject("errors", "User not found");
                return modelAndView;
            }
            LOGGER.info(USER_FOUND + user.getUserId()+ " " + user.getFirstNameUser());
            modelAndView.addObject("user", user);
            return modelAndView;

        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }


}
