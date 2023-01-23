package pl.envelo.moovelo.controller.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class EventOwnerDto {
    private long id;
    private long basicUserId;
    private String firstname;
    private String lastname;
    private List<EventResponseDto> events;

}
