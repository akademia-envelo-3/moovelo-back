package pl.envelo.moovelo.controller.dto.group;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnerDto;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
public class GroupResponseDto {
    private Long id;
    private String name;
    private String description;
    private GroupOwnerDto groupOwner;
    private List<BasicUserDto> groupMembers;
    private List<DisplayEventResponseDto> events;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupResponseDto groupResponseDto = (GroupResponseDto) o;
        return id.equals(groupResponseDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
