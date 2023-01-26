package pl.envelo.moovelo.controller.mapper.actor;

import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.entity.actors.BasicUser;

import static org.junit.jupiter.api.Assertions.*;

class BasicUserMapperTest {

    @Test
    void map() {

        BasicUser entity = new BasicUser();
        entity.setId(2L);
        entity.setFirstname("John");
        entity.setLastname("Doe");

        BasicUserDto dto = BasicUserMapper.map(entity);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getFirstname(), entity.getFirstname());
        assertEquals(dto.getLastname(), entity.getLastname());
    }
}