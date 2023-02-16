package pl.envelo.moovelo.controller.dto.group.groupownership;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class GroupResponseDto {
    private Long id;
    private String name;
    private String description;
    private GroupOwnerDto groupOwnerDto;
}
