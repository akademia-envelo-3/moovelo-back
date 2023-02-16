package pl.envelo.moovelo.service.group;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.repository.group.GroupRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class GroupService {
    private GroupRepository groupRepository;

    public boolean isBasicUserGroupOwner(Long loggedUserId, Long groupId) {
        Optional<Group> groupOptionalById = groupRepository.findById(groupId);
        return groupOptionalById.get().getGroupOwner().getUserId().equals(loggedUserId);
    }
}
