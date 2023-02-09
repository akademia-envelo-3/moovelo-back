package pl.envelo.moovelo.controller.mapper.group.groupownership;

import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnerDto;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnershipRequestDto;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.groups.GroupOwner;

public class GroupOwnerMapper {
    public static GroupOwnerDto mapGroupOwnerToGroupOwnerDto(GroupOwner groupOwner, BasicUser basicUser) {
        return GroupOwnerDto.builder()
                .id(groupOwner.getId())
                .basicUserId(groupOwner.getUserId())
                .firstname(basicUser.getFirstname())
                .lastname(basicUser.getLastname())
                .build();
    }

    public static GroupOwnerDto mapGroupOwnerToGroupOwnerDto(GroupOwner groupOwner) {
        return GroupOwnerDto.builder()
                .id(groupOwner.getId())
                .basicUserId(groupOwner.getUserId())
                .build();
    }

    public static GroupOwner mapGroupOwnershipRequestDtoToGroupOwner(GroupOwnershipRequestDto groupOwnershipRequestDto) {
        GroupOwner groupOwner = new GroupOwner();
        groupOwner.setUserId(groupOwnershipRequestDto.getNewOwnerId());

        return groupOwner;
    }
}