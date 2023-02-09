package pl.envelo.moovelo.controller.mapper.actor;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.actor.UserDto;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.Role;
import pl.envelo.moovelo.entity.actors.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    @Test
    @Disabled
    void map() {

        User entity = new BasicUser();
        entity.setId(1L);
        entity.setEmail("email@envelo.pl");
        entity.setPassword("password");
        //entity.setRole(Role.BASIC_USER);

        UserDto dto = UserMapper.map(entity);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getRole(), entity.getRole());
    }
}