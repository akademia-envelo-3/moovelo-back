package pl.envelo.moovelo.controller.dto.group;

import lombok.Builder;

@Builder
public class GroupInfoDto {
    private long id;
    private long groupId;
    private String name;
    private String description;
}
