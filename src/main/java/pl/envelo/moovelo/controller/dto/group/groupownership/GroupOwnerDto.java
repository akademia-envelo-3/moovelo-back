package pl.envelo.moovelo.controller.dto.group.groupownership;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.group.GroupDto;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
public class GroupOwnerDto {
    private long id;
    private long basicUserId;
    private String firstname;
    private String lastname;
    private List<GroupDto> groups;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupOwnerDto that = (GroupOwnerDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, basicUserId, firstname, lastname, groups);
    }
}
