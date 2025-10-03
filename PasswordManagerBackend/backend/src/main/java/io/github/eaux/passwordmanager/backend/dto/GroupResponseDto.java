package io.github.eaux.passwordmanager.backend.dto;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    private static final Gson gson = new Gson();

    public Group getGroupFromGroupResponseDto() {
        Group group = new Group();
        group.setGroupId(groupId);
        group.setGroupName(groupName);
        group.setGroupDescription(groupDescription);
        return group;
    }

    public GroupResponseDto getGroupResponseDtoFromGroup(Group group) {
        GroupResponseDto dto = new GroupResponseDto();
        dto.setGroupId(group.getGroupId());
        dto.setGroupName(group.getGroupName());
        dto.setGroupDescription(group.getGroupDescription());
        return dto;
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public String toJson(GroupResponseDto groupResponseDto) {
        return gson.toJson(groupResponseDto);
    }

    public String toJson(List<GroupResponseDto> groupResponseDtos) {
        return gson.toJson(groupResponseDtos);
    }

    public GroupResponseDto fromJson(String classJson) {
        return gson.fromJson(classJson, GroupResponseDto.class);
    }

    public List<GroupResponseDto> fromJsonList(String json) {
        return gson.fromJson(json, new TypeToken<List<GroupResponseDto>>() {
        }.getType());
    }
}
