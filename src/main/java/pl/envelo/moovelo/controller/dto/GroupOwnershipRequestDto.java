package pl.envelo.moovelo.controller.dto;

import lombok.Builder;

@Builder
public class GroupOwnershipRequestDto {
    private long id;
    private long newOwnerId;
    private GroupInfoDto groupInfo;
}
