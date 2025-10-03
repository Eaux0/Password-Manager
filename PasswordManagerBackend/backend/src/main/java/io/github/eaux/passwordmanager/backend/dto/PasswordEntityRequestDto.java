package io.github.eaux.passwordmanager.backend.dto;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.github.eaux.passwordmanager.backend.model.UserPassword;
import io.github.eaux.passwordmanager.backend.model.UserPasswordDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordEntityRequestDto {

    private Long groupId;
    Long userPasswordId;
    private String passwordName;
    private String passwordDescription;
    private String passwordType;
    private String passwordUserName;
    private String password;

    private static final Gson gson = new Gson();

    public UserPassword getUserPasswordFromPasswordEntityRequestDto(Long userId) {
        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(userId);
        userPassword.setGroupId(groupId);
        userPassword.setUserPasswordUserName(passwordUserName);
        userPassword.setUserPassword(password);
        return userPassword;
    }

    // Convert this DTO to UserPasswordDetail
    public UserPasswordDetail getUserPasswordDetailFromPasswordEntityRequestDto(Long userId) {
        UserPasswordDetail userPasswordDetail = new UserPasswordDetail();
        userPasswordDetail.setUserId(userId);
        userPasswordDetail.setUserPasswordId(userPasswordId);
        userPasswordDetail.setUserPasswordName(passwordName);
        userPasswordDetail.setUserPasswordDescription(passwordDescription);
        return userPasswordDetail;
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public String toJson(PasswordEntityRequestDto passwordEntityRequestDto) {
        return gson.toJson(passwordEntityRequestDto);
    }

    public String toJson(List<PasswordEntityRequestDto> passwordEntityRequestDtos) {
        return gson.toJson(passwordEntityRequestDtos);
    }

    public PasswordEntityRequestDto fromJson(String classJson) {
        return gson.fromJson(classJson, PasswordEntityRequestDto.class);
    }

    public List<PasswordEntityRequestDto> fromJsonList(String json) {
        return gson.fromJson(json, new TypeToken<List<PasswordEntityRequestDto>>() {
        }.getType());
    }

}
