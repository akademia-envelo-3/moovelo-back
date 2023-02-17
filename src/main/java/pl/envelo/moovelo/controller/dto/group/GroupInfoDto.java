package pl.envelo.moovelo.controller.dto.group;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class GroupInfoDto {
    private Long id;
    private String name;
    private String description;
}
