package pl.envelo.moovelo.controller.mapper.group;

import pl.envelo.moovelo.controller.dto.group.groupownership.GroupResponseDto;
import pl.envelo.moovelo.controller.mapper.group.groupownership.GroupOwnerMapper;
import pl.envelo.moovelo.entity.groups.Group;

public class GroupResponseMapper {
    public static GroupResponseDto mapGroupToGroupResponseMapper(Group group) {
        return new GroupResponseDto(group.getId(),
                group.getGroupInfo().getName(),
                group.getGroupInfo().getDescription(),
                GroupOwnerMapper.mapGroupOwnerToGroupOwnerDto(group.getGroupOwner()));
    }
}
