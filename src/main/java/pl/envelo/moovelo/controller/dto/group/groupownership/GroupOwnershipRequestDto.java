package pl.envelo.moovelo.controller.dto.group.groupownership;

import lombok.Getter;

@Getter
public class GroupOwnershipRequestDto {
    private final long groupId;
    private final long userId;
    private final long newOwnerId;

    public GroupOwnershipRequestDto(long groupId, long userId, long newOwnerId) {
        this.groupId = groupId;
        this.userId = userId;
        this.newOwnerId = newOwnerId;
    }
}
