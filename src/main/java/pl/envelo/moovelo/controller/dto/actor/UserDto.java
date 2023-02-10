package pl.envelo.moovelo.controller.dto.actor;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.entity.actors.Role;

import java.util.Set;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private Role role;
}
