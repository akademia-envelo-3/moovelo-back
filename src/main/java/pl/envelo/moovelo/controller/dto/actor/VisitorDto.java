package pl.envelo.moovelo.controller.dto.actor;

import lombok.Builder;

@Builder
public class VisitorDto {
    private long id;
    private long eventId;
    private String firstname;
    private String lastname;
    private String email;

}
