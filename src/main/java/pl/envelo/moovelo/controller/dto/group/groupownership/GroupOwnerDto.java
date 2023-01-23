package pl.envelo.moovelo.controller.dto.group.groupownership;

import lombok.Builder;
import pl.envelo.moovelo.controller.dto.group.GroupDto;

import java.util.List;

@Builder
public class GroupOwnerDto {
    private long id;
    private long basicUserId;
    private String firstname;
    private String lastname;
    private List<GroupDto> groups;
}
