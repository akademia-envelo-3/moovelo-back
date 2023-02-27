package pl.envelo.moovelo.service.actors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.groups.GroupOwner;
import pl.envelo.moovelo.repository.group.GroupOwnerRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class GroupOwnerService {
    private GroupOwnerRepository groupOwnerRepository;

    public GroupOwner getGroupOwnerByGroupId(Long groupId) {
        log.info("GroupOwnerService - getGroupOwnerByGroupId() - groupId {}", groupId);
        Optional<GroupOwner> groupOwnerOptional = groupOwnerRepository.findByGroupsId(groupId);
        if (groupOwnerOptional.isEmpty()) {
            throw new NoSuchElementException("No group owner for the group with id: : " + groupId + " found");
        } else {
            GroupOwner groupOwner = groupOwnerOptional.get();
            log.info("GroupOwnerService - getGroupOwnerByGroupId() - groupId = {} ,  return groupOwner = {}", groupId, groupOwner);
            return groupOwner;
        }
    }

    public GroupOwner getGroupOwnerByUserId(Long userId) {
        log.info("GroupOwnerService - getGroupOwnerByUserId() - userId = {}", userId);
        Optional<GroupOwner> groupOwnerOptional = groupOwnerRepository.findByUserId(userId);
        return groupOwnerOptional.orElseGet(() -> createNewGroupOwner(userId));
    }

    public boolean isBasicUserGroupOwner(Long userId) {
        return groupOwnerRepository.findByUserId(userId).isPresent();
    }

    private GroupOwner createNewGroupOwner(Long userId) {
        GroupOwner groupOwner = new GroupOwner();
        groupOwner.setUserId(userId);
        return groupOwnerRepository.save(groupOwner);
    }

    public void removeGroupOwnerOwnerWithNoGroups(GroupOwner groupOwner) {
        log.info("GroupOwnerService - removeGroupOwnerWithNoGroups() - groupOwner = '{}'", groupOwner);
        if (groupOwner.getGroups().isEmpty()) {
            groupOwnerRepository.delete(groupOwner);
            log.info("GroupOwnerService - removeGroupOwnerWithNoGroups() - groupOwner removed");
        }
    }
}
