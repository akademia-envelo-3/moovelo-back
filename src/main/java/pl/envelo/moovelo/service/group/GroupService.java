package pl.envelo.moovelo.service.group;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.controller.searchutils.GroupPage;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.groups.GroupInfo;
import pl.envelo.moovelo.entity.groups.GroupOwner;
import pl.envelo.moovelo.repository.group.GroupRepository;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.GroupOwnerService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.BiFunction;

@AllArgsConstructor
@Service
@Slf4j
public class GroupService {

    private GroupOwnerService groupOwnerService;
    private GroupInfoService groupInfoService;
    private BasicUserService basicUserService;
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
        newGroup.setMembers(new HashSet<>());
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

    public Page<Group> getAllGroupsForBasicUser(Long basicUserId, Boolean membership, GroupPage groupPage) {
        log.info("GroupService - getAllGroupsForBasicUser()  - params - basicUser = {}, membership = {}, groupPage = {}",
                basicUserId, membership, groupPage);
        Pageable pageable = getPageable(groupPage);
        BasicUser basicUser = basicUserService.getBasicUserById(basicUserId);
        Page<Group> basicUserGroups = groupRepository.findAllGroupsWhereUserIsMember(basicUser, pageable);
        Page<Group> notBasicUserGroups = groupRepository.findAllGroupsWhereUserIsNotMember(basicUser, pageable);
        Page<Group> resultPage = membership ? basicUserGroups : notBasicUserGroups;
        log.info("GroupService - getAllGroupsForBasicUser() - return {}", resultPage.toString());
        return resultPage;
    }

    public void joinGroup(Long userId, Group group) {
        log.info("GroupService - joinGroup() - basicUserId = {}, group = {}", userId, group);
        BasicUser basicUser = basicUserService.getBasicUserById(userId);
        Set<Group> userGroups = basicUser.getGroups();
        if (userGroups == null) {
            userGroups = new HashSet<>();
        }
        userGroups.add(group);
        updateGroupAndBasicUser(group, basicUser, Set::add);
        log.info("GroupService - joinGroup() - basicUserId = {}, group = {} - group joined by user", userId, group);
    }

    public void leaveGroup(Long userId, Group group) {
        log.info("GroupService - leaveGroup() - basicUserId = {}, group = {}", userId, group);
        BasicUser basicUser = basicUserService.getBasicUserById(userId);
        Set<Group> userGroups = basicUser.getGroups();
        userGroups.remove(group);
        updateGroupAndBasicUser(group, basicUser, Set::remove);
        log.info("GroupService - leaveGroup() - basicUserId = {}, group = {} - group left by user", userId, group);
    }

    private void updateGroupAndBasicUser(Group group,
                                         BasicUser basicUser,
                                         BiFunction<Set<BasicUser>, BasicUser, Boolean> membersInteraction) {
        Set<BasicUser> members = group.getMembers();
        membersInteraction.apply(members, basicUser);
        group.setGroupSize(members.size());
        groupRepository.save(group);
        basicUserService.updateBasicUser(basicUser);
    }

    public Page<Group> getAllGroupsWithoutFiltering(GroupPage groupPage) {
        Pageable pageable = getPageable(groupPage);
        return groupRepository.findAll(pageable);
    }

    private Pageable getPageable(GroupPage groupPage) {
        String sortFromParam = groupPage.getSort();
        if (sortFromParam.equals("name")) {
            sortFromParam = "groupInfo.name";
        }
        Sort sort = Sort.by(groupPage.getSortOrder(), sortFromParam);
        return PageRequest.of(groupPage.getPageNumber(), groupPage.getPageSize(), sort);
    }
}
