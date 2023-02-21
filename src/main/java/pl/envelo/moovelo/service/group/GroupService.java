package pl.envelo.moovelo.service.group;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.controller.searchUtils.GroupPage;
import pl.envelo.moovelo.controller.searchUtils.PagingUtils;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.groups.GroupInfo;
import pl.envelo.moovelo.entity.groups.GroupOwner;
import pl.envelo.moovelo.repository.group.GroupRepository;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.GroupOwnerService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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
        newGroup.setMembers(new ArrayList<>());
        newGroup.setEvents(new ArrayList<>());
        return newGroup;
    }

    public Page<Group> getAllGroupsForBasicUser(Long basicUserId, Boolean membership, GroupPage groupPage) {
        log.info("GroupService - getAllGroupsForBasicUser()  - params - basicUser = {}, membership = {}, groupPage = {}",
                basicUserId, membership, groupPage);
        Pageable pageable = getPageable(groupPage);
        List<Group> allBasicUserGroups = basicUserService.getAllBasicUserGroups(basicUserId);
        List<Group> allNotBasicUserGroups = getNonBasicUserGroups(allBasicUserGroups);
        List<Group> allGroups = membership ?  allBasicUserGroups : allNotBasicUserGroups;
        log.info("GroupService - getAllGroupsForBasicUser() - return {}", allGroups.toString());
        return PagingUtils.listToPage(pageable, allGroups);
    }

    private List<Group> getNonBasicUserGroups(List<Group> allBasicUserGroups) {
        List <Group> allNotBasicUserGroups= groupRepository.findAll()
                .stream()
                .filter(group -> !allBasicUserGroups.contains(group)).toList();
        return allNotBasicUserGroups;
    }

    public Page<Group> getAllGroupsWithoutFiltering(GroupPage groupPage) {
        Pageable pageable = getPageable(groupPage);
        return groupRepository.findAll(pageable);
    }

    private Pageable getPageable(GroupPage groupPage) {
        Sort sort = Sort.by(groupPage.getSortOrder(), groupPage.getSort());
        return PageRequest.of(groupPage.getPageNumber(), groupPage.getPageSize(), sort);
    }
}
