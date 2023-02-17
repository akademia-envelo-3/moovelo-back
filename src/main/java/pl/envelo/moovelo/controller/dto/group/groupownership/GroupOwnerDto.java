package pl.envelo.moovelo.controller.dto.group.groupownership;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Builder
@Getter
public class GroupOwnerDto {
    private Long basicUserId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupOwnerDto that = (GroupOwnerDto) o;
        return Objects.equals(basicUserId, that.basicUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basicUserId);
    }
}
