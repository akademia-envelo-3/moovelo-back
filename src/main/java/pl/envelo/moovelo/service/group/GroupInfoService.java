package pl.envelo.moovelo.service.group;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.groups.GroupInfo;
import pl.envelo.moovelo.repository.group.GroupInfoRepository;

@AllArgsConstructor
@Service
public class GroupInfoService {
    private GroupInfoRepository groupInfoRepository;

    public GroupInfo createGroupInfo(GroupInfo groupInfo) {
        return groupInfoRepository.save(groupInfo);
    }

    public void updateGroupInfo(GroupInfo groupInfo) {
        groupInfoRepository.save(groupInfo);
    }
}
