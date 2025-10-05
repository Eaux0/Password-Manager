package io.github.eaux.passwordmanager.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.eaux.passwordmanager.backend.dto.GroupRequestDto;
import io.github.eaux.passwordmanager.backend.dto.GroupResponseDto;
import io.github.eaux.passwordmanager.backend.dto.PasswordEntityRequestDto;
import io.github.eaux.passwordmanager.backend.dto.PasswordEntityResponseDto;
import io.github.eaux.passwordmanager.backend.model.Group;
import io.github.eaux.passwordmanager.backend.security.HashesUtil;
import io.github.eaux.passwordmanager.backend.service.GroupService;
import io.github.eaux.passwordmanager.backend.service.RedisSessionService;
import io.github.eaux.passwordmanager.backend.service.UserPasswordDetailService;
import io.github.eaux.passwordmanager.backend.service.UserPasswordService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class PasswordManagerController {

    @Autowired
    private UserPasswordService userPasswordService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserPasswordDetailService userPasswordDetailsService;

    @Autowired
    private RedisSessionService redisSessionService;

    private GroupResponseDto emptyGroupResponseDto = new GroupResponseDto();
    private PasswordEntityResponseDto emptyPasswordEntityResponseDto = new PasswordEntityResponseDto();
    private PasswordEntityRequestDto emptyPasswordEntityRequestDto = new PasswordEntityRequestDto();
    private GroupRequestDto emptyGroupRequestDto = new GroupRequestDto();
    private HashesUtil hashesUtil = new HashesUtil();

    @GetMapping("/{sessionId}/groups")
    public String getAllGroupsForUser(@PathVariable Long sessionId) {
        Long userId = getLoggedInUserId(sessionId);
        List<Group> groups = groupService.getAllGroupsForUserId(userId);
        List<GroupResponseDto> groupResponseDtos = groups.stream()
                .map(group -> emptyGroupResponseDto.getGroupResponseDtoFromGroup(group))
                .toList();

        return encryptRespose(emptyGroupResponseDto.toJson(groupResponseDtos), sessionId);
    }

    @GetMapping("/{sessionId}/passwords")
    public String getAllPasswordsForUser(@PathVariable Long sessionId) {
        Long userId = getLoggedInUserId(sessionId);
        List<Long> userPasswordIds = userPasswordService.getAllUserPasswordIdsForUserId(userId);
        List<PasswordEntityResponseDto> passwordEntityResponseDtos = userPasswordIds.stream().map(userPasswordId -> {
            var userPassword = userPasswordService.getUserPasswordByUserPasswordId(userPasswordId);
            var userPasswordDetail = userPasswordDetailsService
                    .getAllUserPasswordDetailsForUserPasswordId(userPasswordId).stream().findFirst().orElse(null);
            return emptyPasswordEntityResponseDto
                    .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
        }).toList();
        return encryptRespose(emptyPasswordEntityResponseDto.toJson(passwordEntityResponseDtos), sessionId);
    }

    @GetMapping("/{sessionId}/groups/{groupId}/passwords")
    public String getAllPasswordsForGroup(@PathVariable Long sessionId, @PathVariable Long groupId) {
        List<Long> userPasswordIds = userPasswordService.getAllUserPasswordIdsForGroupId(groupId);
        List<PasswordEntityResponseDto> passwordEntityResponseDtos = userPasswordIds.stream().map(userPasswordId -> {
            var userPassword = userPasswordService.getUserPasswordByUserPasswordId(userPasswordId);
            var userPasswordDetail = userPasswordDetailsService
                    .getAllUserPasswordDetailsForUserPasswordId(userPasswordId).stream().findFirst().orElse(null);
            return emptyPasswordEntityResponseDto
                    .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
        }).toList();
        return encryptRespose(emptyPasswordEntityResponseDto.toJson(passwordEntityResponseDtos), sessionId);
    }

    @GetMapping("/{sessionId}/groups/{groupId}")
    public String getGroupByGroupId(@PathVariable Long sessionId, @PathVariable Long groupId) {
        Group group = groupService.getGroupByGroupId(groupId);
        return encryptRespose(emptyGroupResponseDto.getGroupResponseDtoFromGroup(group).toJson(), sessionId);
    }

    @GetMapping("/{sessionId}/passwords/{passwordId}")
    public String getPasswordByPasswordId(@PathVariable Long sessionId, @PathVariable Long passwordId) {
        var userPassword = userPasswordService.getUserPasswordByUserPasswordId(passwordId);
        var userPasswordDetail = userPasswordDetailsService
                .getAllUserPasswordDetailsForUserPasswordId(passwordId).stream().findFirst().orElse(null);
        return encryptRespose(emptyPasswordEntityResponseDto
                .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail).toJson(), sessionId);
    }

    @DeleteMapping("/{sessionId}/groups/{groupId}")
    public void deleteGroupByGroupId(@PathVariable Long sessionId, @PathVariable Long groupId) {
        groupService.deleteGroupByGroupId(groupId);
    }

    @DeleteMapping("/{sessionId}/passwords/{passwordId}")
    public void deletePasswordByPasswordId(@PathVariable Long sessionId, @PathVariable Long passwordId) {
        userPasswordService.deleteUserPasswordByUserPasswordId(passwordId);
        userPasswordDetailsService.deleteUserPasswordDetailsByUserPasswordId(passwordId);
    }

    @PutMapping("/{sessionId}/groups/{groupId}")
    public String modifyGroupByGroupId(@PathVariable Long sessionId, @PathVariable Long groupId,
            @RequestBody String modifiedGroupString) {
        GroupRequestDto modifiedGroup = emptyGroupRequestDto.fromJson(decryptedPayload(modifiedGroupString, sessionId));
        Group group = groupService.modifyGroupByGroupId(groupId,
                modifiedGroup.getGroupFromGroupRequestDto());
        return encryptRespose(emptyGroupResponseDto.getGroupResponseDtoFromGroup(group).toJson(), sessionId);
    }

    @PutMapping("/{sessionId}/passwords/{passwordId}")
    public String modifyPasswordByPasswordId(@PathVariable Long sessionId, @PathVariable Long passwordId,
            @RequestBody String modifiedPasswordString) {
        PasswordEntityRequestDto modifiedPassword = emptyPasswordEntityRequestDto
                .fromJson(decryptedPayload(modifiedPasswordString, sessionId));
        var userPassword = userPasswordService.modifyUserPasswordByUserPasswordId(passwordId,
                modifiedPassword.getUserPasswordFromPasswordEntityRequestDto(getLoggedInUserId(sessionId)));
        var userPasswordDetail = userPasswordDetailsService.modifyUserPasswordDetailByUserPasswordDetailId(
                userPasswordDetailsService.getUserPasswordDetailIdByUserPasswordId(passwordId),
                modifiedPassword.getUserPasswordDetailFromPasswordEntityRequestDto(getLoggedInUserId(sessionId)));
        return encryptRespose(emptyPasswordEntityResponseDto
                .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail).toJson(), sessionId);
    }

    @PostMapping("/{sessionId}/groups")
    public String createGroup(@PathVariable Long sessionId, @RequestBody String newGroupString) {
        GroupRequestDto newGroup = emptyGroupRequestDto.fromJson(decryptedPayload(newGroupString, sessionId));
        Group group = groupService.createGroup(newGroup.getGroupFromGroupRequestDto());
        return encryptRespose(emptyGroupResponseDto.getGroupResponseDtoFromGroup(group).toJson(), sessionId);
    }

    @PostMapping("/{sessionId}/passwords")
    public String createPassword(@PathVariable Long sessionId, @RequestBody String newPasswordString) {
        PasswordEntityRequestDto newPassword = emptyPasswordEntityRequestDto
                .fromJson(decryptedPayload(newPasswordString, sessionId));
        var userPassword = userPasswordService.createUserPassword(
                newPassword.getUserPasswordFromPasswordEntityRequestDto(getLoggedInUserId(sessionId)));
        var userPasswordDetail = userPasswordDetailsService.createUserPasswordDetail(
                newPassword.getUserPasswordDetailFromPasswordEntityRequestDto(getLoggedInUserId(sessionId)));
        return encryptRespose(emptyPasswordEntityResponseDto
                .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail).toJson(), sessionId);
    }

    @GetMapping("/{sessionId}/search/passwords?searchString={searchString}")
    public String searchPasswords(@PathVariable Long sessionId, @RequestParam String searchString) {
        Long userId = getLoggedInUserId(sessionId);
        List<Long> userPasswordIds = userPasswordService.searchUserPasswords(userId, searchString).stream()
                .map(userPassword -> userPassword.getUserPasswordId()).toList();
        List<PasswordEntityResponseDto> passwordEntityResponseDtos = userPasswordIds.stream().map(userPasswordId -> {
            var userPassword = userPasswordService.getUserPasswordByUserPasswordId(userPasswordId);
            var userPasswordDetail = userPasswordDetailsService
                    .getAllUserPasswordDetailsForUserPasswordId(userPasswordId).stream().findFirst().orElse(null);
            return emptyPasswordEntityResponseDto
                    .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
        }).toList();
        return encryptRespose(emptyPasswordEntityResponseDto.toJson(passwordEntityResponseDtos), sessionId);
    }

    @GetMapping("/{sessionId}/search/groups?searchString={searchString}")
    public String searchGroups(@PathVariable Long sessionId, @RequestParam String searchString) {
        Long userId = getLoggedInUserId(sessionId);
        List<Group> groups = groupService.searchGroup(userId, searchString);
        List<GroupResponseDto> groupResponseDtos = groups.stream()
                .map(group -> emptyGroupResponseDto.getGroupResponseDtoFromGroup(group))
                .toList();
        return encryptRespose(emptyGroupResponseDto.toJson(groupResponseDtos), sessionId);
    }

    public Long getLoggedInUserId(Long sessionId) {
        return redisSessionService.getUserIdForSessionId(sessionId);
    }

    public String getAESKeyString(Long sessionId) {
        return redisSessionService.getAESKeyStringForSessionId(sessionId);
    }

    public String encryptRespose(String apiResponse, Long sessionId) {
        // Return encrypted respose
        try {
            return hashesUtil.encryptResponse(apiResponse, getAESKeyString(sessionId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decryptedPayload(String apiPayload, Long sessionId) {
        // Retrun decrypted payload
        try {
            return hashesUtil.decryptpayload(apiPayload, getAESKeyString(sessionId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}