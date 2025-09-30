package io.github.eaux.passwordmanager.backend.dto;

import com.google.gson.Gson;

import io.github.eaux.passwordmanager.backend.model.UserPassword;
import io.github.eaux.passwordmanager.backend.model.UserPasswordDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordEntityResponseDto {
    private Long groupId;
    private String passwordName;
    private String passwordDescription;
    private String passwordUserName;
    private String password;
    private Long passwordId;

    private static final Gson gson = new Gson();

    public UserPassword getUserPasswordFromPasswordEntityResponseDto(Long userId) {
        UserPassword userPassword = new UserPassword();
        userPassword.setUserPasswordId(passwordId);
        userPassword.setUserId(userId);
        userPassword.setGroupId(groupId);
        userPassword.setUserPasswordUserName(passwordUserName);
        userPassword.setUserPassword(password);
        return userPassword;
    }

    // Convert this DTO to UserPasswordDetail
    public UserPasswordDetail getUserPasswordDetailFromPasswordEntityResponseDto(Long userId, Long passwordDetailId) {
        UserPasswordDetail userPasswordDetail = new UserPasswordDetail();
        userPasswordDetail.setUserPasswordDetailId(passwordDetailId);
        userPasswordDetail.setUserId(userId);
        userPasswordDetail.setUserPasswordId(passwordId);
        userPasswordDetail.setUserPasswordName(passwordName);
        userPasswordDetail.setUserPasswordDescription(passwordDescription);
        return userPasswordDetail;
    }

    // Create DTO from UserPassword and UserPasswordDetail
    public PasswordEntityResponseDto getPasswordEntityResponseDtoFromUserPasswords(UserPassword userPassword,
            UserPasswordDetail userPasswordDetail) {
        PasswordEntityResponseDto dto = new PasswordEntityResponseDto();
        dto.setPasswordId(userPassword.getUserPasswordId());
        dto.setGroupId(userPassword.getGroupId());
        dto.setPasswordUserName(userPassword.getUserPasswordUserName());
        dto.setPassword(userPassword.getUserPassword());
        dto.setPasswordName(userPasswordDetail.getUserPasswordName());
        dto.setPasswordDescription(userPasswordDetail.getUserPasswordDescription());
        return dto;
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public static PasswordEntityResponseDto fromJson(String classJson) {
        return gson.fromJson(classJson, PasswordEntityResponseDto.class);
    }

}
