package io.github.eaux.passwordmanager.backend.dto;

import java.util.HashMap;
import java.util.Map;

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

    public String toString() {
        Map<String, String> currClass = new HashMap<>();
        currClass.put("userId", userId.toString());
        currClass.put("groupId", groupId.toString());
        currClass.put("groupName", groupName.toString());
        currClass.put("groupDescription", groupDescription.toString());
        return currClass.toString();
    }

    public Group getGroupFromGroupRequestDto(GroupRequestDto groupRequestDto) {
        return new Group(groupRequestDto.getGroupId(), groupRequestDto.getUserId(), groupRequestDto.getGroupName(),
                groupRequestDto.getGroupDescription());
    }

    public GroupRequestDto getGroupRequestDtoFromString(String stringDto) {

        stringDto = stringDto.substring(1, stringDto.length() - 1);
        String[] vals = stringDto.split(",");

        return null;
    }

}
