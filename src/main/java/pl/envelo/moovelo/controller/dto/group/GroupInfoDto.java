package pl.envelo.moovelo.controller.dto.group;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupInfoDto {
    private long id;
    private long groupId;
    private String name;
    private String description;
}
