package pl.envelo.moovelo.controller.dto.group;

import lombok.*;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupOwnerDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupListResponseDto {
    private Long id;
    private GroupOwnerDto groupOwnerDto;
    private boolean isUserMember;
    private String name;
    private String description;
    private int numberOfMembers;
}
