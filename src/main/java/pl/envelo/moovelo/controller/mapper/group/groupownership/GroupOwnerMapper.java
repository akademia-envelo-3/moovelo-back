package pl.envelo.moovelo.controller.mapper.group.groupownership;

import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnerDto;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnershipRequestDto;
import pl.envelo.moovelo.controller.mapper.group.GroupMapper;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.groups.GroupOwner;

import java.util.stream.Collectors;

public class GroupOwnerMapper {
    public static GroupOwnerDto map(GroupOwner groupOwner, BasicUser basicUser) {
        return  GroupOwnerDto.builder()
                .id(groupOwner.getId())
                .basicUserId(groupOwner.getUserId())
                .firstname(basicUser.getFirstname())
                .lastname(basicUser.getLastname())
                .build();
    }

    public static GroupOwnerDto map(GroupOwner groupOwner) {
        return  GroupOwnerDto.builder()
                .id(groupOwner.getId())
                .basicUserId(groupOwner.getUserId())
                .build();
    }

    public static GroupOwner map(GroupOwnershipRequestDto groupOwnershipRequestDto) {
        GroupOwner groupOwner = new GroupOwner();
        groupOwner.setUserId(groupOwnershipRequestDto.getNewOwnerId());

        return groupOwner;
    }
}
