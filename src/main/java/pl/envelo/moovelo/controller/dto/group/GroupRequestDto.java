package pl.envelo.moovelo.controller.dto.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.groups.GroupInfo;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class GroupRequestDto {
    private GroupInfoDto groupInfoDto;
}
