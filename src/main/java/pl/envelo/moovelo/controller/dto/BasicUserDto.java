package pl.envelo.moovelo.controller.dto;

import lombok.Builder;

@Builder
public class BasicUserDto {
    private long id;
    private String firstname;
    private String lastname;
}
