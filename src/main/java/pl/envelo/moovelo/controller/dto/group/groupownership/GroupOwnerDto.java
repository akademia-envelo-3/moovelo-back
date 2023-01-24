package pl.envelo.moovelo.controller.dto.group.groupownership;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.group.GroupDto;

import java.util.List;

@Builder
@Getter
public class GroupOwnerDto {
    private long id;
    private long basicUserId;
    private String firstname;
    private String lastname;
    private List<GroupDto> groups;
}
