package pl.envelo.moovelo.controller.mapper.group;

import pl.envelo.moovelo.controller.dto.group.GroupDto;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapper;
import pl.envelo.moovelo.controller.mapper.group.groupownership.GroupOwnerMapper;
import pl.envelo.moovelo.entity.groups.Group;

import java.util.stream.Collectors;

public class GroupMapper {
    public static GroupDto mapGroupToGroupDto(Group group) {
        return GroupDto
                .builder()
                .id(group.getId())
                .name(group.getGroupInfo().getName())
                .description(group.getGroupInfo().getDescription())
                .groupOwner(GroupOwnerMapper.mapGroupOwnerToGroupOwnerDto(group.getGroupOwner()))
                .numberOfMembers(group.getMembers().size())
                .groupMembers(group.getMembers()
                        .stream()
                        .map(BasicUserMapper::map)
                        .collect(Collectors.toList()))
                //TODO: after merging method: EventResponseDto map(Event) in class EventMapper
//              .events(group.getEvents()
//                       .stream()
//                       .map(EventMapper::map)
//                       .collect(Collectors.toList()))
                .build();

    }
}