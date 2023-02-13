package pl.envelo.moovelo.controller.dto.actor;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicUserDto that = (BasicUserDto) o;
        return id == that.id && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname);
    }
}
