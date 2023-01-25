package pl.envelo.moovelo.controller.dto.actor;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VisitorDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;

}
