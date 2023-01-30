package pl.envelo.moovelo.controller.dto.actor;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class VisitorDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitorDto that = (VisitorDto) o;
        return Objects.equals(id, that.id) && Objects.equals(firstname, that.firstname)
                && Objects.equals(lastname, that.lastname) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, email);
    }
}
