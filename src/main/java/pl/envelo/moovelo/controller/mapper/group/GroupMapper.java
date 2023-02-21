package pl.envelo.moovelo.controller.mapper.group;

import pl.envelo.moovelo.controller.dto.group.*;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapper;
import pl.envelo.moovelo.controller.mapper.group.groupownership.GroupOwnerMapper;
import pl.envelo.moovelo.entity.groups.Group;

import java.util.stream.Collectors;

public class GroupMapper {
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
                        .collect(Collectors.toList()))
                .events(group.getEvents()
                        .stream()
                        .map(EventMapper::mapInternalEventToEventResponseDto)
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
                .numberOfMembers(group.getMembers().size())
                .build();
    }

    public static GroupResponseDtoForEvent mapGroupToGroupResponseDtoForEvent(Group group) {
        return new GroupResponseDtoForEvent(group.getId(), group.getGroupInfo().getName());
    }

    public static Group mapGroupRequestDtoToGroup(GroupRequestDto groupRequestDto) {
        Group group = new Group();
        group.setGroupInfo(GroupInfoMapper.mapGroupInfoDtoToGroupInfo(groupRequestDto.getGroupInfo()));
        return group;
    }
}