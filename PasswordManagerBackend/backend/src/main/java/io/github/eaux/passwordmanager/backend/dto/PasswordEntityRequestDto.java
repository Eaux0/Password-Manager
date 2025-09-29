package io.github.eaux.passwordmanager.backend.dto;

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

    public UserPassword getUserPasswordFromPasswordEntityRequestDto(Long userId,
            PasswordEntityRequestDto passwordEntityRequestDto) {
        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(userId);
        userPassword.setGroupId(passwordEntityRequestDto.getGroupId());
        userPassword.setUserPasswordUserName(passwordEntityRequestDto.getPasswordUserName());
        userPassword.setUserPassword(passwordEntityRequestDto.getPassword());
        return userPassword;
    }

    public UserPasswordDetail getUserPasswordDetailFromPasswordEntityRequestDto(Long userId, Long userPasswordDetailId,
            PasswordEntityRequestDto passwordEntityRequestDto) {
        UserPasswordDetail userPasswordDetail = new UserPasswordDetail();
        userPasswordDetail.setUserId(userId);
        userPasswordDetail.setUserPasswordId(passwordEntityRequestDto.getUserPasswordId());
        userPasswordDetail.setUserPasswordName(passwordEntityRequestDto.getPasswordName());
        userPasswordDetail.setUserPasswordDescription(passwordEntityRequestDto.getPasswordDescription());
        return userPasswordDetail;
    }

}
