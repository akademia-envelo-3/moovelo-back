package pl.envelo.moovelo.controller.dto.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnerDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupListResponseDto {
    private Long id;
    private GroupOwnerDto groupOwner;
    private boolean isUserMember;
    private String name;
    private String description;
    private int numberOfMembers;
}
