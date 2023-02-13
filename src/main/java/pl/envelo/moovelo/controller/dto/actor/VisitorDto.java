package pl.envelo.moovelo.controller.dto.actor;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Builder
public class VisitorDto {
    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    @Email
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VisitorDto that = (VisitorDto) o;
        return Objects.equals(firstname, that.firstname)
                && Objects.equals(lastname, that.lastname)
                && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, email);
    }
}
