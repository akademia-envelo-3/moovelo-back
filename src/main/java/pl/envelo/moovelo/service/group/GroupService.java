package pl.envelo.moovelo.service.group;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.groups.GroupInfo;
import pl.envelo.moovelo.entity.groups.GroupOwner;
import pl.envelo.moovelo.repository.group.GroupRepository;
import pl.envelo.moovelo.service.actors.GroupOwnerService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class GroupService {

    private GroupOwnerService groupOwnerService;
    private GroupInfoService groupInfoService;
    private GroupRepository groupRepository;

    public Group createNewGroup(Group group, Long userId) {
        log.info("GroupService - createNewGroup() - groupOwnerUserId = {}", userId);
        Group newGroup = setNewGroupFields(group, userId);
        groupRepository.save(newGroup);
        log.info("GroupService - createNewGroup() - return group = {}", newGroup);
        return newGroup;
    }

    private Group setNewGroupFields(Group group, Long userId) {
        GroupOwner groupOwner = groupOwnerService.getGroupOwnerByUserId(userId);
        Group newGroup = new Group();
        GroupInfo groupInfo = groupInfoService.createGroupInfo(group.getGroupInfo());
        newGroup.setGroupInfo(groupInfo);
        newGroup.setGroupOwner(groupOwner);
        newGroup.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        newGroup.setMembers(new ArrayList<>());
        newGroup.setEvents(new ArrayList<>());
        return newGroup;
    }

    public void removeGroup(Long groupId) {
        log.info("GroupService - removeGroup(groupId = '{}')", groupId);
        GroupOwner groupOwner = groupOwnerService.getGroupOwnerByGroupId(groupId);
        groupRepository.deleteById(groupId);
        groupOwnerService.removeGroupOwnerOwnerWithNoGroups(groupOwner);
        log.info("GroupService - removeGroup(groupId = '{}') - group removed", groupId);
    }

    public Group getGroupById(Long groupId) {
        log.info("GroupService - getGroupById() - groupId = {}", groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            log.info("GroupService - getGroupById() - groupId = {} - return group = {}", groupId, group);
            return group;
        } else {
            log.error("No group with id = {}", groupId, new NoSuchElementException());
            throw new NoSuchElementException("No group with id: " + groupId);
        }
    }

    public void updateEventById(Group group, GroupInfo groupInfo) {
        String groupName = groupInfo.getName();
        Optional<Group> groupInfoOptional = groupRepository.findByGroupInfoName(groupName);
        if (groupInfoOptional.isPresent()) {
            throw new IllegalArgumentException("Group with name: " + groupName + " already exists");
        }
    }
}
