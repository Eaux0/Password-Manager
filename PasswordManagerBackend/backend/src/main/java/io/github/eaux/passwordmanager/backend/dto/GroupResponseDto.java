package io.github.eaux.passwordmanager.backend.dto;

import io.github.eaux.passwordmanager.backend.model.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDto {
    private Long groupId;
    private String groupName;
    private String groupDescription;

    public Group getGroupFromGroupResponseDto(GroupResponseDto groupResponseDto) {
        Group group = new Group();
        group.setGroupId(groupResponseDto.getGroupId());
        group.setGroupName(groupResponseDto.getGroupName());
        group.setGroupDescription(groupResponseDto.getGroupDescription());
        return group;
    }

    public GroupResponseDto getGroupResponseDtoFromGroup(Group group) {
        GroupResponseDto groupResponseDto = new GroupResponseDto();
        groupResponseDto.setGroupId(group.getGroupId());
        groupResponseDto.setGroupName(group.getGroupName());
        groupResponseDto.setGroupDescription(group.getGroupDescription());
        return groupResponseDto;
    }

}
