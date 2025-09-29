package io.github.eaux.passwordmanager.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.eaux.passwordmanager.backend.model.UserPasswordDetail;
import io.github.eaux.passwordmanager.backend.repository.UserPasswordDetailRepository;

@Service
public class UserPasswordDetailService {

    @Autowired
    private UserPasswordDetailRepository userPasswordDetailRepository;

    public List<UserPasswordDetail> getAllUserPasswordDetailsForUserId(Long userId) {
        return userPasswordDetailRepository.findAllByUserId(userId);
    }

    public List<UserPasswordDetail> getAllUserPasswordDetailsForUserPasswordId(Long userPasswordId) {
        return userPasswordDetailRepository.findAllByUserPasswordId(userPasswordId);
    }

    public UserPasswordDetail getUserPasswordDetailByUserPasswordDetailId(Long userPasswordDetailId) {
        return userPasswordDetailRepository.findById(userPasswordDetailId).orElse(null);
    }

    public void deleteUserPasswordDetailByUserPasswordDetailId(Long userPasswordDetailId) {
        userPasswordDetailRepository.deleteById(userPasswordDetailId);
    }

    public UserPasswordDetail modifyUserPasswordDetailByUserPasswordDetailId(Long userPasswordDetailId,
            UserPasswordDetail modifiedUserPasswordDetail) {
        return userPasswordDetailRepository.save(modifiedUserPasswordDetail);
    }

    public UserPasswordDetail createUserPasswordDetail(UserPasswordDetail newUserPasswordDetail) {
        return userPasswordDetailRepository.save(newUserPasswordDetail);
    }

    public List<UserPasswordDetail> searchUserPasswordDetails(Long userId, String searchString) {
        return userPasswordDetailRepository.findAllByUserIdAndSearchString(userId, searchString);
    }

    public Long getUserPasswordDetailIdByUserPasswordId(Long userPasswordId) {
        return userPasswordDetailRepository.findAllByUserPasswordId(userPasswordId).get(0).getUserPasswordDetailId();
    }

    public void deleteUserPasswordDetailsByUserPasswordId(Long userPasswordId) {
        Long userPasswordDetailId = getUserPasswordDetailIdByUserPasswordId(userPasswordId);
        deleteUserPasswordDetailByUserPasswordDetailId(userPasswordDetailId);
    }
}
