package pl.envelo.moovelo.controller.dto.group;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.event.EventResponseDto;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnerDto;

import java.util.List;

@Builder
@Getter
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
