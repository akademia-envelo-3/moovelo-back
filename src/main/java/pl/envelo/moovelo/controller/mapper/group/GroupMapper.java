package pl.envelo.moovelo.controller.mapper.group;

import pl.envelo.moovelo.controller.dto.group.GroupDto;
import pl.envelo.moovelo.controller.mapper.group.groupownership.GroupOwnerMapper;
import pl.envelo.moovelo.entity.groups.Group;

public class GroupMapper {
    public static GroupDto map(Group group){
        GroupDto groupDto = GroupDto
                .builder()
                .id(group.getId())
                .name(group.getGroupInfo().getName())
                .description(group.getGroupInfo().getDescription())
                .groupOwner(GroupOwnerMapper.map(group.getGroupOwner()))
                .numberOfMembers(group.getMembers().size())
                .build();




    }
}
