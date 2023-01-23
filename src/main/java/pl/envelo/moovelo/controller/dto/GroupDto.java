package pl.envelo.moovelo.controller.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class GroupDto {
    private long id;

    private String name;
    private String description;
    private GroupOwnerDto groupOwner;
    private boolean isUserMember;
    private int numberOfMembers;
    private List<BasicUserDto> groupMembers;
    private List<EventResponseDto> events;

}
