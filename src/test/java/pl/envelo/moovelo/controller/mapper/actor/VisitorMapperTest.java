package pl.envelo.moovelo.controller.mapper.actor;

import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.Visitor;

import static org.junit.jupiter.api.Assertions.*;

class VisitorMapperTest {

    @Test
    void map() {
        Visitor entity = new Visitor();
        entity.setId(1L);
        entity.setFirstname("John");
        entity.setLastname("Doe");
        entity.setEmail("email@envelo.pl");

        VisitorDto dto = VisitorMapper.map(entity);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getFirstname(), entity.getFirstname());
        assertEquals(dto.getLastname(), entity.getLastname());
        assertEquals(dto.getEmail(), entity.getEmail());

    }

    @Test
    void testMap() {
        VisitorDto dto = VisitorDto
                .builder()
                .firstname("John")
                .lastname("Doe")
                .email("email@envelo.pl")
                .build();

        Visitor entity = VisitorMapper.map(dto);

        assertEquals(entity.getFirstname(), dto.getFirstname());
        assertEquals(entity.getLastname(), dto.getLastname());
        assertEquals(entity.getEmail(), dto.getEmail());

    }
}