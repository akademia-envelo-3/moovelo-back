package pl.envelo.moovelo.controller.dto.actor;

import lombok.Builder;
import pl.envelo.moovelo.entity.actors.Role;

@Builder
public class UserDto {
    private String email;
    private String password;
    private Role role;
}
