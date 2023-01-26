package pl.envelo.moovelo.controller.mapper.group;

import pl.envelo.moovelo.controller.dto.group.GroupInfoDto;
import pl.envelo.moovelo.entity.groups.GroupInfo;

public class GroupInfoMapper {

    public static GroupInfo map(GroupInfoDto groupInfoDto) {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setName(groupInfoDto.getName());
        groupInfo.setDescription(groupInfoDto.getDescription());
    }
}
