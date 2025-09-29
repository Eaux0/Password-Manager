package io.github.eaux.passwordmanager.backend.dto;

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

    public UserPassword getUserPasswordFromPasswordEntityResponseDto(Long userId,
            PasswordEntityResponseDto passwordEntityResponseDto) {
        UserPassword userPassword = new UserPassword();
        userPassword.setUserPasswordId(passwordEntityResponseDto.getPasswordId());
        userPassword.setUserId(userId);
        userPassword.setGroupId(passwordEntityResponseDto.getGroupId());
        userPassword.setUserPasswordUserName(passwordEntityResponseDto.getPasswordUserName());
        userPassword.setUserPassword(passwordEntityResponseDto.getPassword());
        return userPassword;
    }

    public UserPasswordDetail getUserPasswordDetailFromPasswordEntityResponseDto(Long userId, Long passwordDetailId,
            PasswordEntityResponseDto passwordEntityResponseDto) {
        UserPasswordDetail userPasswordDetail = new UserPasswordDetail();
        userPasswordDetail.setUserPasswordDetailId(passwordDetailId);
        userPasswordDetail.setUserId(userId);
        userPasswordDetail.setUserPasswordId(passwordEntityResponseDto.getPasswordId());
        userPasswordDetail.setUserPasswordName(passwordEntityResponseDto.getPasswordName());
        userPasswordDetail.setUserPasswordDescription(passwordEntityResponseDto.getPasswordDescription());
        return userPasswordDetail;
    }

    public PasswordEntityResponseDto getPasswordEntityResponseDtoFromUserPasswords(UserPassword userPassword,
            UserPasswordDetail userPasswordDetail) {
        PasswordEntityResponseDto passwordEntityResponseDto = new PasswordEntityResponseDto();
        passwordEntityResponseDto.setPasswordId(userPassword.getUserPasswordId());
        passwordEntityResponseDto.setGroupId(userPassword.getGroupId());
        passwordEntityResponseDto.setPasswordUserName(userPassword.getUserPasswordUserName());
        passwordEntityResponseDto.setPassword(userPassword.getUserPassword());
        passwordEntityResponseDto.setPasswordName(userPasswordDetail.getUserPasswordName());
        passwordEntityResponseDto.setPasswordDescription(userPasswordDetail.getUserPasswordDescription());
        return passwordEntityResponseDto;
    }

}
