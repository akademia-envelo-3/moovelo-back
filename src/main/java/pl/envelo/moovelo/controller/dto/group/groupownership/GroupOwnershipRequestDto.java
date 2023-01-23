package pl.envelo.moovelo.controller.dto.group.groupownership;

import lombok.Builder;
import pl.envelo.moovelo.controller.dto.group.GroupInfoDto;

@Builder
public class GroupOwnershipRequestDto {
    private long groupId;
    private long newOwnerId;
    private GroupInfoDto groupInfo;
}
