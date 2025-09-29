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

    @SuppressWarnings("unused")
    @Autowired
    private UserPasswordService userPasswordService;
    @SuppressWarnings("unused")
    @Autowired
    private GroupService groupService;
    @SuppressWarnings("unused")
    @Autowired
    private UserPasswordDetailService userPasswordDetailsService;

    @GetMapping("/groups")
    public List<GroupResponseDto> getAllGroupsForUser() {
        Long userId = getLoggedInUserId();
        List<Group> groups = groupService.getAllGroupsForUserId(userId);
        List<GroupResponseDto> groupResponseDtos = groups.stream()
                .map(group -> new GroupResponseDto().getGroupResponseDtoFromGroup(group))
                .toList();
        return groupResponseDtos;
    }

    @GetMapping("/passwords")
    public List<PasswordEntityResponseDto> getAllPasswordsForUser() {
        Long userId = getLoggedInUserId();
        List<Long> userPasswordIds = userPasswordService.getAllUserPasswordIdsForUserId(userId);
        List<PasswordEntityResponseDto> passwordEntityResponseDtos = userPasswordIds.stream().map(userPasswordId -> {
            var userPassword = userPasswordService.getUserPasswordByUserPasswordId(userPasswordId);
            var userPasswordDetail = userPasswordDetailsService
                    .getAllUserPasswordDetailsForUserPasswordId(userPasswordId).stream().findFirst().orElse(null);
            return new PasswordEntityResponseDto()
                    .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
        }).toList();
        return passwordEntityResponseDtos;
    }

    @GetMapping("/groups/{groupId}/passwords")
    public List<PasswordEntityResponseDto> getAllPasswordsForGroup(@PathVariable Long groupId) {
        List<Long> userPasswordIds = userPasswordService.getAllUserPasswordIdsForGroupId(groupId);
        List<PasswordEntityResponseDto> passwordEntityResponseDtos = userPasswordIds.stream().map(userPasswordId -> {
            var userPassword = userPasswordService.getUserPasswordByUserPasswordId(userPasswordId);
            var userPasswordDetail = userPasswordDetailsService
                    .getAllUserPasswordDetailsForUserPasswordId(userPasswordId).stream().findFirst().orElse(null);
            return new PasswordEntityResponseDto()
                    .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
        }).toList();
        return passwordEntityResponseDtos;
    }

    @GetMapping("/groups/{groupId}")
    public GroupResponseDto getGroupByGroupId(@PathVariable Long groupId) {
        Group group = groupService.getGroupByGroupId(groupId);
        return new GroupResponseDto().getGroupResponseDtoFromGroup(group);
    }

    @GetMapping("/passwords/{passwordId}")
    public PasswordEntityResponseDto getPasswordByPasswordId(@PathVariable Long passwordId) {
        var userPassword = userPasswordService.getUserPasswordByUserPasswordId(passwordId);
        var userPasswordDetail = userPasswordDetailsService
                .getAllUserPasswordDetailsForUserPasswordId(passwordId).stream().findFirst().orElse(null);
        return new PasswordEntityResponseDto()
                .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
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
    public GroupResponseDto modifyGroupByGroupId(@PathVariable Long groupId, GroupRequestDto modifiedGroup) {
        Group group = groupService.modifyGroupByGroupId(groupId,
                modifiedGroup.getGroupFromGroupRequestDto(modifiedGroup));
        return new GroupResponseDto().getGroupResponseDtoFromGroup(group);
    }

    @PutMapping("/passwords/{passwordId}")
    public PasswordEntityResponseDto modifyPasswordByPasswordId(@PathVariable Long passwordId,
            PasswordEntityRequestDto modifiedPassword) {
        var userPassword = userPasswordService.modifyUserPasswordByUserPasswordId(passwordId,
                modifiedPassword.getUserPasswordFromPasswordEntityRequestDto(getLoggedInUserId(), modifiedPassword));
        var userPasswordDetail = userPasswordDetailsService.modifyUserPasswordDetailByUserPasswordDetailId(
                userPasswordDetailsService.getUserPasswordDetailIdByUserPasswordId(passwordId),
                modifiedPassword.getUserPasswordDetailFromPasswordEntityRequestDto(getLoggedInUserId(),
                        userPasswordDetailsService.getUserPasswordDetailIdByUserPasswordId(passwordId),
                        modifiedPassword));
        return new PasswordEntityResponseDto()
                .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
    }

    @PostMapping("/groups")
    public GroupResponseDto createGroup(@RequestBody GroupRequestDto newGroup) {
        Group group = groupService.createGroup(newGroup.getGroupFromGroupRequestDto(newGroup));
        return new GroupResponseDto().getGroupResponseDtoFromGroup(group);
    }

    @PostMapping("/passwords")
    public PasswordEntityResponseDto createPassword(@RequestBody PasswordEntityRequestDto newPassword) {
        var userPassword = userPasswordService.createUserPassword(
                newPassword.getUserPasswordFromPasswordEntityRequestDto(getLoggedInUserId(), newPassword));
        var userPasswordDetail = userPasswordDetailsService.createUserPasswordDetail(
                newPassword.getUserPasswordDetailFromPasswordEntityRequestDto(getLoggedInUserId(),
                        null, newPassword));
        return new PasswordEntityResponseDto()
                .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
    }

    @GetMapping("/generatePassword")
    public String generatePassword(@RequestBody int length,
            @RequestBody boolean includeSpecialChars,
            @RequestBody boolean includeNumbers) {
        return userPasswordService.generatePassword(length, includeSpecialChars, includeNumbers);
    }

    @GetMapping("/search/passwords?searchString={searchString}")
    public List<PasswordEntityResponseDto> searchPasswords(@RequestParam String searchString) {
        Long userId = getLoggedInUserId();
        List<Long> userPasswordIds = userPasswordService.searchUserPasswords(userId, searchString).stream()
                .map(userPassword -> userPassword.getUserPasswordId()).toList();
        List<PasswordEntityResponseDto> passwordEntityResponseDtos = userPasswordIds.stream().map(userPasswordId -> {
            var userPassword = userPasswordService.getUserPasswordByUserPasswordId(userPasswordId);
            var userPasswordDetail = userPasswordDetailsService
                    .getAllUserPasswordDetailsForUserPasswordId(userPasswordId).stream().findFirst().orElse(null);
            return new PasswordEntityResponseDto()
                    .getPasswordEntityResponseDtoFromUserPasswords(userPassword, userPasswordDetail);
        }).toList();
        return passwordEntityResponseDtos;
    }

    @GetMapping("/search/groups?searchString={searchString}")
    public List<GroupResponseDto> searchGroups(@RequestParam String searchString) {
        Long userId = getLoggedInUserId();
        List<Group> groups = groupService.searchGroup(userId, searchString);
        List<GroupResponseDto> groupResponseDtos = groups.stream()
                .map(group -> new GroupResponseDto().getGroupResponseDtoFromGroup(group))
                .toList();
        return groupResponseDtos;
    }

    public Long getLoggedInUserId() {
        return 0L;
    }

}
