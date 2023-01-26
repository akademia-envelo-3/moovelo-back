package pl.envelo.moovelo.controller.mapper.group.groupownership;

import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnerDto;
import pl.envelo.moovelo.entity.groups.GroupOwner;

public class GroupOwnerMapper {
    public static GroupOwnerDto map(GroupOwner groupOwner) {
        return  GroupOwnerDto.builder()
                .id(groupOwner.getId())
                .basicUserId(groupOwner.getBasicUserId())
                .build();

    }
}
