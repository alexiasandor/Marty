package com.example.martybackend.service;

import com.example.martybackend.dtos.UserDto;
import com.example.martybackend.dtos.mappers.UserMapper;
import com.example.martybackend.entity.User;
import com.example.martybackend.repository.OrdRepository;
import com.example.martybackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.martybackend.constants.EmailConstants.ROL_INCORRECT;
import static com.example.martybackend.constants.UserConstants.*;

@Service

public class UserService {
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdRepository.class);


    @Autowired

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    /**
     * This method creates a user
     * @param userDto the user which has dto type
     * @return return success messages or error messages
     */
    public String insert(UserDto userDto) {
        try{
            LOGGER.info(START_PROCESSING);
            User user1 = UserMapper.fromDto(userDto);
            user1= userRepository.save(user1);
            LOGGER.info(USER_INSERTED_SUCCESSFULLY);
            return (USER_INSERTED_SUCCESSFULLY);}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return (USER_NOT_INSERTED);
        }
    }

    /**
     * This method returns all users from database.
     * @return a list of users
     */
    public List<UserDto> findUser(){
        try {
            LOGGER.info(START_PROCESSING);
            List<User> userList = userRepository.findAll();
            LOGGER.info("Users" + userList.stream().map(UserMapper::toEntity).collect(Collectors.toList()));
            return userList.stream().map(UserMapper::toEntity).collect(Collectors.toList());
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * This method returns only one user.
     *  @param userId the user's id which we use to search
     *   @return the user which has id = userId
     */
    public UserDto findUserById(Long userId)
    {   try {
            LOGGER.info(START_PROCESSING);
            Optional<User> optionalUser = userRepository.findById(userId);
            LOGGER.info("User " + UserMapper.toEntity(optionalUser.get()));
            return UserMapper.toEntity(optionalUser.get());
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }

    }

    /**
     This method updates a user
     * @param userDto the user with the updated information
     * @return returns success message and updated user
     */
    public String updateUser (UserDto userDto){
        try {
            LOGGER.info(START_PROCESSING);
            User existingUser = userRepository.findById(userDto.getUserId()).get();
            existingUser.setFirstNameUser(userDto.getFirstNameUser());
            existingUser.setSecondNameUser(userDto.getSecondNameUser());
            existingUser.setRole(userDto.getRole());
            existingUser.setAge(userDto.getAge());
            existingUser.setEmailUser(userDto.getEmailUser());
            existingUser.setPasswordUser(userDto.getPasswordUser());

            User updatedUser = userRepository.save(existingUser);
            LOGGER.info(USER_UPDATED_SUCCESSFULLY);
            return ("User" + UserMapper.toEntity(updatedUser) + "successfully updated");
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return(USER_NOT_UPDATED_SUCCESSFULLY);
        }
    }

    /**
     * This method deletes a user
     * @param userId user's id we want to delete
     * @return success message
     */
    public String deleteUser(Long userId){
        try{
            LOGGER.info(START_PROCESSING);
            userRepository.deleteById(userId);
            LOGGER.info(USER_DELETED_SUCCESSFULLY);
            return(USER_DELETED_SUCCESSFULLY);}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return(USER_NOT_DELETED);
        }
    }
    /**
     * This method searches for a user by email and password.
     * @param email    The email of the user to be searched
     * @param password password The password of the user to be searched
     * @return True if the user is found and the password matches, false otherwise
     */
    public boolean findUserByEmailAndPassword(String email, String password) {
        try{
        Optional<User> optionalUser = userRepository.findByEmailUser(email);
            LOGGER.info(START_PROCESSING);
            User user = optionalUser.get();
            if (password.equals(user.getPasswordUser())) {
                LOGGER.info(USER_FOUND);
                return true;
            } else {
                LOGGER.info(USER_NOT_FOUND);
                return false;

            }

        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return false;
        }
    }
    /**
     * This method retrieves the role of a user by email.
     * @param email The email of the user
     * @return The role of the user or error if the user is not found
     */
    public String getUserRole(String email) {
        try{
            LOGGER.info(START_PROCESSING);
            Optional<User> optionalUser = userRepository.findByEmailUser(email);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                LOGGER.info(USER_FOUND);
               return user.getRole();
            }
            else {
                LOGGER.info(ROL_INCORRECT);
                return ROL_INCORRECT;
            }
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }

    }

    /**
     * This method searches for users by their role.
     *
     * @return A list of all users with the role of waiter
     */
    public List<UserDto> findWaiters() {
        try {
            LOGGER.info(START_PROCESSING);
            List<User> waitersList = userRepository.findAll();
            LOGGER.info("Waiters: " +waitersList.stream()
                    .filter(user -> "chelner".equalsIgnoreCase(user.getRole()))
                    .map(UserMapper::toEntity)
                    .toList());

            return waitersList.stream()
                    .filter(user -> "chelner".equalsIgnoreCase(user.getRole()))
                    .map(UserMapper::toEntity)
                    .collect(Collectors.toList());

        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}
