package io.github.eaux.passwordmanager.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.eaux.passwordmanager.backend.model.UserPassword;
import io.github.eaux.passwordmanager.backend.repository.UserPasswordRepository;

@Service
public class UserPasswordService {
    @Autowired
    private UserPasswordRepository userPasswordRepository;

    public List<UserPassword> getAllUserPasswordsForUserId(Long userId) {
        return userPasswordRepository.findAllByUserId(userId);
    }

    public List<UserPassword> getAllUserPasswordsForGroupId(Long groupId) {
        return userPasswordRepository.findAllByGroupId(groupId);
    }

    public UserPassword getUserPasswordByUserPasswordId(Long userPasswordId) {
        return userPasswordRepository.findById(userPasswordId).orElse(null);
    }

    public void deleteUserPasswordByUserPasswordId(Long userPasswordId) {
        userPasswordRepository.deleteById(userPasswordId);
    }

    public UserPassword modifyUserPasswordByUserPasswordId(Long userPasswordId, UserPassword modifiedUserPassword) {
        modifiedUserPassword.setUserPasswordId(userPasswordId);
        return userPasswordRepository.save(modifiedUserPassword);
    }

    public UserPassword createUserPassword(UserPassword newUserPassword) {
        return userPasswordRepository.save(newUserPassword);
    }

    public String generatePassword(int length, boolean includeSpecialChars, boolean includeNumbers) {
        return "generatedPassword";
    }

    public List<UserPassword> searchUserPasswords(Long userId, String searchString) {
        return userPasswordRepository.findAllByUserIdAndSearchString(userId, searchString);
    }

    public List<Long> getAllUserPasswordIdsForUserId(Long userId) {
        return userPasswordRepository.findAllByUserId(userId).stream()
                .map(UserPassword::getUserPasswordId)
                .collect(Collectors.toList());
    }

    public List<Long> getAllUserPasswordIdsForGroupId(Long groupId) {
        return userPasswordRepository.findAllByGroupId(groupId).stream()
                .map(UserPassword::getUserPasswordId)
                .collect(Collectors.toList());
    }

}
