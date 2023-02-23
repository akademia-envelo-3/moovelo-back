package pl.envelo.moovelo.controller.mapper.group.groupownership;

import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnerDto;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.groups.GroupOwner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupOwnerMapperTest {

    @Test
    void map() {
        GroupOwner entity = new GroupOwner();
        entity.setId(1L);
        entity.setUserId(12L);
        BasicUser user = new BasicUser();
        user.setFirstname("John");
        user.setLastname("Doe");

        GroupOwnerDto dto = GroupOwnerMapper.mapGroupOwnerToGroupOwnerDto(entity);

        assertEquals(entity.getUserId(), dto.getBasicUserId());
    }

//    @Test
//    void testMap() {
//
//        GroupOwnershipRequestDto dto = new GroupOwnershipRequestDto(10, 11, 13);
//
//        GroupOwner entity = GroupOwnerMapper.mapGroupOwnershipRequestDtoToGroupOwner(dto);
//
//        assertEquals(dto.newOwnerId(), entity.getUserId());
//
//    }
}