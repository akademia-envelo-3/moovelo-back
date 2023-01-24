package pl.envelo.moovelo.controller.dto.actor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BasicUserDto {
    private long id;
    private String firstname;
    private String lastname;

    public BasicUserDto(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
