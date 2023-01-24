package pl.envelo.moovelo.controller.dto.actor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.entity.actors.Role;

@Getter
@Builder
public class UserDto {
    private long id;
    private String email;
    private String password;
    private Role role;


}
