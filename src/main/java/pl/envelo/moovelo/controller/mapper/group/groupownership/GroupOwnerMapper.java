package pl.envelo.moovelo.controller.mapper.group.groupownership;

import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnerDto;
import pl.envelo.moovelo.entity.groups.GroupOwner;

public class GroupOwnerMapper {
    public static GroupOwnerDto mapGroupOwnerToGroupOwnerDto(GroupOwner groupOwner) {
        return GroupOwnerDto.builder()
                .basicUserId(groupOwner.getUserId())
                .build();
    }
}