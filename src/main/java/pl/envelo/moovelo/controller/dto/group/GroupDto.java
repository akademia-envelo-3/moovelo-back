package pl.envelo.moovelo.controller.dto.group;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.event.EventResponseDto;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnerDto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupDto groupDto = (GroupDto) o;
        return id == groupDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
