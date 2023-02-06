package pl.envelo.moovelo.controller.mapper.actor;

import pl.envelo.moovelo.controller.dto.actor.UserDto;
import pl.envelo.moovelo.entity.actors.User;

public class UserMapper {

    public static UserDto map(User user) {

        return UserDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRoles())
                .build();
    }
}
