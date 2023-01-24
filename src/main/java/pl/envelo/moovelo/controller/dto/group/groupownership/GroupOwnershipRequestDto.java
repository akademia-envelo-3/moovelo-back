package pl.envelo.moovelo.controller.dto.group.groupownership;

import lombok.Getter;

@Getter
public class GroupOwnershipRequestDto {
    private long groupId;
    private long userId;
    private long newOwnerId;

    public GroupOwnershipRequestDto(long groupId, long userId, long newOwnerId) {
        this.groupId = groupId;
        this.userId = userId;
        this.newOwnerId = newOwnerId;
    }
}
