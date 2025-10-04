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

    private Long sessionId;

    private GroupResponseDto emptyGroupResponseDto = new GroupResponseDto();
    private PasswordEntityResponseDto emptyPasswordEntityResponseDto = new PasswordEntityResponseDto();
    private PasswordEntityRequestDto emptyPasswordEntityRequestDto = new PasswordEntityRequestDto();
    private GroupRequestDto emptyGroupRequestDto = new GroupRequestDto();

    @GetMapping("/groups")
    public String getAllGroupsForUser() {
        Long userId = getLoggedInUserId();
        List<Group> groups = groupService.getAllGroupsForUserId(userId);
        List<GroupResponseDto> groupResponseDtos = groups.stream()
                .map(group -> emptyGroupResponseDto.getGroupResponseDtoFromGroup(group))
                .toList();

        return encryptRespose(emptyGroupResponseDto.toJson(groupResponseDtos));
    }

    @GetMapping("/passwords")
    public String getAllPasswordsForUser() {
        Long userId = getLoggedInUserId();
        List<Long> userPasswordIds = userPasswordService.getAllUserPasswordIdsForUserId(userId);
        List<PasswordEntityResponseDto> passwordEntityResponseDtos = userPasswordIds.stream().map(userPasswordId -> {
            var userPassword = userPasswordService.getUserPasswordByUserPasswordId(userPasswordId);
            var userPasswordDetail = userPasswordDetailsService
                    .getAllUserPasswordDetailsForUserPasswordId(userPasswordId).stream().findFirst().orElse(null);
            return emptyPasswordEntityResponseDto
                    .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
        }).toList();
        return encryptRespose(emptyPasswordEntityResponseDto.toJson(passwordEntityResponseDtos));
    }

    @GetMapping("/groups/{groupId}/passwords")
    public String getAllPasswordsForGroup(@PathVariable Long groupId) {
        List<Long> userPasswordIds = userPasswordService.getAllUserPasswordIdsForGroupId(groupId);
        List<PasswordEntityResponseDto> passwordEntityResponseDtos = userPasswordIds.stream().map(userPasswordId -> {
            var userPassword = userPasswordService.getUserPasswordByUserPasswordId(userPasswordId);
            var userPasswordDetail = userPasswordDetailsService
                    .getAllUserPasswordDetailsForUserPasswordId(userPasswordId).stream().findFirst().orElse(null);
            return emptyPasswordEntityResponseDto
                    .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
        }).toList();
        return encryptRespose(emptyPasswordEntityResponseDto.toJson(passwordEntityResponseDtos));
    }

    @GetMapping("/groups/{groupId}")
    public String getGroupByGroupId(@PathVariable Long groupId) {
        Group group = groupService.getGroupByGroupId(groupId);
        return encryptRespose(emptyGroupResponseDto.getGroupResponseDtoFromGroup(group).toJson());
    }

    @GetMapping("/passwords/{passwordId}")
    public String getPasswordByPasswordId(@PathVariable Long passwordId) {
        var userPassword = userPasswordService.getUserPasswordByUserPasswordId(passwordId);
        var userPasswordDetail = userPasswordDetailsService
                .getAllUserPasswordDetailsForUserPasswordId(passwordId).stream().findFirst().orElse(null);
        return encryptRespose(emptyPasswordEntityResponseDto
                .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail).toJson());
    }

    @DeleteMapping("/groups/{groupId}")
    public void deleteGroupByGroupId(@PathVariable Long groupId) {
        groupService.deleteGroupByGroupId(groupId);
    }

    @DeleteMapping("/passwords/{passwordId}")
    public void deletePasswordByPasswordId(@PathVariable Long passwordId) {
        userPasswordService.deleteUserPasswordByUserPasswordId(passwordId);
        userPasswordDetailsService.deleteUserPasswordDetailsByUserPasswordId(passwordId);
    }

    @PutMapping("/groups/{groupId}")
    public String modifyGroupByGroupId(@PathVariable Long groupId, @RequestBody String modifiedGroupString) {
        GroupRequestDto modifiedGroup = emptyGroupRequestDto.fromJson(decryptedPayload(modifiedGroupString));
        Group group = groupService.modifyGroupByGroupId(groupId,
                modifiedGroup.getGroupFromGroupRequestDto());
        return encryptRespose(emptyGroupResponseDto.getGroupResponseDtoFromGroup(group).toJson());
    }

    @PutMapping("/passwords/{passwordId}")
    public String modifyPasswordByPasswordId(@PathVariable Long passwordId,
            @RequestBody String modifiedPasswordString) {
        PasswordEntityRequestDto modifiedPassword = emptyPasswordEntityRequestDto
                .fromJson(decryptedPayload(modifiedPasswordString));
        var userPassword = userPasswordService.modifyUserPasswordByUserPasswordId(passwordId,
                modifiedPassword.getUserPasswordFromPasswordEntityRequestDto(getLoggedInUserId()));
        var userPasswordDetail = userPasswordDetailsService.modifyUserPasswordDetailByUserPasswordDetailId(
                userPasswordDetailsService.getUserPasswordDetailIdByUserPasswordId(passwordId),
                modifiedPassword.getUserPasswordDetailFromPasswordEntityRequestDto(getLoggedInUserId()));
        return encryptRespose(emptyPasswordEntityResponseDto
                .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail).toJson());
    }

    @PostMapping("/groups")
    public String createGroup(@RequestBody String newGroupString) {
        GroupRequestDto newGroup = emptyGroupRequestDto.fromJson(decryptedPayload(newGroupString));
        Group group = groupService.createGroup(newGroup.getGroupFromGroupRequestDto());
        return encryptRespose(emptyGroupResponseDto.getGroupResponseDtoFromGroup(group).toJson());
    }

    @PostMapping("/passwords")
    public String createPassword(@RequestBody String newPasswordString) {
        PasswordEntityRequestDto newPassword = emptyPasswordEntityRequestDto
                .fromJson(decryptedPayload(newPasswordString));
        var userPassword = userPasswordService.createUserPassword(
                newPassword.getUserPasswordFromPasswordEntityRequestDto(getLoggedInUserId()));
        var userPasswordDetail = userPasswordDetailsService.createUserPasswordDetail(
                newPassword.getUserPasswordDetailFromPasswordEntityRequestDto(getLoggedInUserId()));
        return encryptRespose(emptyPasswordEntityResponseDto
                .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail).toJson());
    }

    @PostMapping("/session/{sessionId}")
    public Boolean getSessionId(@PathVariable Long sessionId) {
        this.sessionId = sessionId;
        return true;
    }

    @GetMapping("/search/passwords?searchString={searchString}")
    public String searchPasswords(@RequestParam String searchString) {
        Long userId = getLoggedInUserId();
        List<Long> userPasswordIds = userPasswordService.searchUserPasswords(userId, searchString).stream()
                .map(userPassword -> userPassword.getUserPasswordId()).toList();
        List<PasswordEntityResponseDto> passwordEntityResponseDtos = userPasswordIds.stream().map(userPasswordId -> {
            var userPassword = userPasswordService.getUserPasswordByUserPasswordId(userPasswordId);
            var userPasswordDetail = userPasswordDetailsService
                    .getAllUserPasswordDetailsForUserPasswordId(userPasswordId).stream().findFirst().orElse(null);
            return emptyPasswordEntityResponseDto
                    .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
        }).toList();
        return encryptRespose(emptyPasswordEntityResponseDto.toJson(passwordEntityResponseDtos));
    }

    @GetMapping("/search/groups?searchString={searchString}")
    public String searchGroups(@RequestParam String searchString) {
        Long userId = getLoggedInUserId();
        List<Group> groups = groupService.searchGroup(userId, searchString);
        List<GroupResponseDto> groupResponseDtos = groups.stream()
                .map(group -> emptyGroupResponseDto.getGroupResponseDtoFromGroup(group))
                .toList();
        return encryptRespose(emptyGroupResponseDto.toJson(groupResponseDtos));
    }

    public Long getLoggedInUserId() {
        return redisSessionService.getUserIdForSessionId(this.sessionId);
    }

    public String getAESKeyString() {
        return redisSessionService.getAESKeyStringForSessionId(this.sessionId);
    }

    public Long getSessionId() {
        return this.sessionId;
    }

    public String encryptRespose(String apiResponse) {
        // return webClient.post().uri("/api/encryptData/" +
        // getSessionId().toString()).bodyValue(apiResponse).retrieve()
        // .bodyToMono(String.class)
        // .block();

        // Return encrypted respose
        return null;
    }

    public String decryptedPayload(String apiPayload) {
        // return webClient.post().uri("/api/decryptData/" +
        // getSessionId().toString()).bodyValue(apiPayload).retrieve()
        // .bodyToMono(String.class)
        // .block();

        // Retrun decrypted payload
        return null;
    }

}