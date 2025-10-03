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
public class GroupRequestDto {
    private Long userId;
    private Long groupId;
    private String groupName;
    private String groupDescription;

    private static final Gson gson = new Gson();

    public Group getGroupFromGroupRequestDto() {
        return new Group(groupId, userId, groupName, groupDescription);
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public String toJson(GroupRequestDto groupRequestDto) {
        return gson.toJson(groupRequestDto);
    }

    public String toJson(List<GroupRequestDto> groupRequestDtos) {
        return gson.toJson(groupRequestDtos);
    }

    public GroupRequestDto fromJson(String classJson) {
        return gson.fromJson(classJson, GroupRequestDto.class);
    }

    public List<GroupRequestDto> fromJsonList(String json) {
        return gson.fromJson(json, new TypeToken<List<GroupRequestDto>>() {
        }.getType());
    }

}
