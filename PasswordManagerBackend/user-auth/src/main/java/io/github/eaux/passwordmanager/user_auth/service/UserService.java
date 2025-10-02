package io.github.eaux.passwordmanager.user_auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.eaux.passwordmanager.user_auth.models.User;
import io.github.eaux.passwordmanager.user_auth.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Boolean isPasswordCorrect(Long userId, String passwordHash) {
        return passwordHash.equals(userRepository.getPasswordHashByUserId(userId));
    }

    public User getUserByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Boolean doesUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findUserByUserId(userId);
    }

    public void deleteUserByUserId(Long userId) {
        userRepository.deleteById(userId);
    }

    public User modifyUserByUserId(Long userId, User modifiedUser) {
        modifiedUser.setUserId(userId);
        return userRepository.save(modifiedUser);
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }
}
