package pl.envelo.moovelo.controller.dto.group.groupownership;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public final class GroupOwnershipRequestDto {
    private Long newOwnerUserId;

    @Override
    public String toString() {
        return "GroupOwnershipRequestDto[" +
                "newOwnerUserId=" + newOwnerUserId + ']';
    }
}
