package pl.envelo.moovelo.controller.mapper.group.groupownership;

import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnerDto;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnershipRequestDto;
import pl.envelo.moovelo.controller.mapper.group.GroupMapper;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.groups.GroupOwner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GroupOwnerMapperTest {

    @Test
    void map() {
        GroupOwner entity = new GroupOwner();
        entity.setId(1L);
        entity.setBasicUserId(12L);
        Group group1 = new Group();
        group1.setId(10L);
        Group group2 = new Group();
        group2.setId(11L);
        entity.setGroups(List.of(group1, group2));
        BasicUser user = new BasicUser();
        user.setFirstname("John");
        user.setLastname("Doe");

        GroupOwnerDto dto = GroupOwnerMapper.map(entity, user);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getBasicUserId(), dto.getBasicUserId());
        assertEquals(user.getFirstname(), dto.getFirstname());
        assertEquals(user.getLastname(), dto.getLastname());
        assertEquals(entity.getGroups().stream().map(GroupMapper::map).collect(Collectors.toList()), dto.getGroups() );


    }

    @Test
    void testMap() {

        GroupOwnershipRequestDto dto = new GroupOwnershipRequestDto(10, 11, 13);

        GroupOwner entity = GroupOwnerMapper.map(dto);

        assertEquals(dto.getNewOwnerId(), entity.getBasicUserId());

    }
}