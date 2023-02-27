package pl.envelo.moovelo.controller.mapper.group;

import pl.envelo.moovelo.controller.dto.group.GroupListResponseDto;
import pl.envelo.moovelo.controller.dto.group.GroupRequestDto;
import pl.envelo.moovelo.controller.dto.group.GroupResponseDto;
import pl.envelo.moovelo.controller.dto.group.GroupResponseDtoForEvent;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.group.groupownership.GroupOwnerMapper;
import pl.envelo.moovelo.entity.groups.Group;

import java.util.stream.Collectors;

public class GroupMapper {
    private static final int FIRST_THREE_GROUP_MEMBERS = 3;

    public static GroupResponseDto mapGroupToGroupResponseDto(Group group) {
        return GroupResponseDto
                .builder()
                .id(group.getId())
                .name(group.getGroupInfo().getName())
                .description(group.getGroupInfo().getDescription())
                .groupOwner(GroupOwnerMapper.mapGroupOwnerToGroupOwnerDto(group.getGroupOwner()))
                .groupMembers(group.getMembers()
                        .stream()
                        .map(BasicUserMapper::map)
                        .limit(FIRST_THREE_GROUP_MEMBERS)
                        .collect(Collectors.toList()))
                .build();

    }

    public static GroupListResponseDto mapGroupToGroupListResponseDto(Group group, boolean isGroupMember) {
        return GroupListResponseDto.builder()
                .id(group.getId())
                .groupOwner(GroupOwnerMapper.mapGroupOwnerToGroupOwnerDto(group.getGroupOwner()))
                .isUserMember(isGroupMember)
                .name(group.getGroupInfo().getName())
                .description(group.getGroupInfo().getDescription())
                .numberOfMembers(group.getGroupSize())
                .build();
    }

    public static GroupResponseDtoForEvent mapGroupToGroupResponseDtoForEvent(Group group) {
        GroupResponseDtoForEvent groupResponseDtoForEvent;
        if (group != null) {
            groupResponseDtoForEvent = new GroupResponseDtoForEvent();
            groupResponseDtoForEvent.setId(group.getId());
            groupResponseDtoForEvent.setName(group.getGroupInfo().getName());
        } else groupResponseDtoForEvent = null;
        return groupResponseDtoForEvent;
    }

    public static Group mapGroupRequestDtoToGroup(GroupRequestDto groupRequestDto) {
        Group group = new Group();
        group.setGroupInfo(GroupInfoMapper.mapGroupInfoDtoToGroupInfo(groupRequestDto.getGroupInfo()));
        return group;
    }
}