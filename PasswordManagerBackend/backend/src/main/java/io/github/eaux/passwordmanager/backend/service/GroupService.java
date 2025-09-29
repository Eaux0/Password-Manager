package io.github.eaux.passwordmanager.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.eaux.passwordmanager.backend.model.Group;
import io.github.eaux.passwordmanager.backend.repository.GroupRepository;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getAllGroupsForUserId(Long userId) {
        return groupRepository.findAllByUserId(userId);
    }

    public Group getGroupByGroupId(Long groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }

    public void deleteGroupByGroupId(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    public Group modifyGroupByGroupId(Long groupId, Group modifiedGroup) {
        modifiedGroup.setGroupId(groupId);
        return groupRepository.save(modifiedGroup);
    }

    public Group createGroup(Group newGroup) {
        return groupRepository.save(newGroup);
    }

    public List<Group> searchGroup(Long userId, String searchString) {
        return groupRepository.findAllByUserIdAndSearchString(userId, searchString);
    }

}
