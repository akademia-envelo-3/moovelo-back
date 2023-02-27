package pl.envelo.moovelo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.dto.OwnershipRequestDto;
import pl.envelo.moovelo.controller.dto.group.GroupInfoDto;
import pl.envelo.moovelo.controller.dto.group.GroupListResponseDto;
import pl.envelo.moovelo.controller.dto.group.GroupRequestDto;
import pl.envelo.moovelo.controller.dto.group.GroupResponseDto;
import pl.envelo.moovelo.controller.mapper.group.GroupInfoMapper;
import pl.envelo.moovelo.controller.mapper.group.GroupMapper;
import pl.envelo.moovelo.controller.searchutils.GroupPage;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.groups.GroupInfo;
import pl.envelo.moovelo.exception.UnauthorizedRequestException;
import pl.envelo.moovelo.service.AuthorizationService;
import pl.envelo.moovelo.service.group.GroupService;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/groups")
@Slf4j
public class GroupController {
    private GroupService groupService;
    private AuthorizationService authorizationService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GroupResponseDto> createNewGroup(@RequestBody GroupRequestDto groupRequestDto) {
        log.info("GroupController - () - createNewGroup()");
        Group groupFromDto = GroupMapper.mapGroupRequestDtoToGroup(groupRequestDto);
        Long userId = authorizationService.getLoggedBasicUserId();
        Group group = groupService.createNewGroup(groupFromDto, userId);
        GroupResponseDto groupResponseDto = GroupMapper.mapGroupToGroupResponseDto(group);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(group.getId())
                .toUri();

        log.info("GroupController - () return createNewGroup() - dto {}", groupResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(groupResponseDto);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Page<GroupListResponseDto>> getAllGroups(Long ownerUserId, Boolean membership, GroupPage groupPage) {
        log.info("GroupController - getAllGroups()");
        Page<Group> groups;
        groups = getGroupsWithFiltering(ownerUserId, membership, groupPage);
        Page<GroupListResponseDto> groupListResponseDtoPage =
                groups.map(group -> GroupMapper.mapGroupToGroupListResponseDto(group, authorizationService.isLoggedUserGroupMember(group)));
        log.info("GroupController - getAllGroups() - return groupListResponseDtoPage = {}", groupListResponseDtoPage);
        return new ResponseEntity<>(groupListResponseDtoPage, HttpStatus.OK);
    }

    private Page<Group> getGroupsWithFiltering(Long ownerUserId, Boolean membership, GroupPage groupPage) {
        Page<Group> groups;
        if (authorizationService.isLoggedUserBasicUser() && membership != null) {
            groups = groupService.getAllGroupsForBasicUser(authorizationService.getLoggedBasicUserId(), membership, groupPage);
        } else if (authorizationService.isLoggedUserAdmin() && ownerUserId != null) {
            groups = groupService.getAllGroupsByGroupOwnerUserId(ownerUserId, groupPage);
        } else {
            groups = groupService.getAllGroupsWithoutFiltering(groupPage);
        }
        return groups;
    }

    @DeleteMapping("{groupId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> removeGroupById(@PathVariable Long groupId) {
        log.info("GroupController - removeGroupById(groupId = '{}')", groupId);

        if (!authorizationService.isLoggedUserAdmin() && !authorizationService.isLoggedUserGroupOwner(groupId)) {
            throw new UnauthorizedRequestException("You must be the owner of the group to delete it!");
        }

        groupService.removeGroup(groupId);

        log.info("GroupController - removeGroupById(groupId = '{}') removed", groupId);

        Map<String, String> body = getBody("Successfully removed the group");

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(body);
    }

    @GetMapping("/{groupId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<GroupResponseDto> getGroupById(@PathVariable Long groupId) {
        log.info("GroupController - () - getGroupById() - groupId = {}", groupId);
        Group group = groupService.getGroupById(groupId);
        GroupResponseDto groupResponseDto = GroupMapper.mapGroupToGroupResponseDto(group);
        log.info("GroupController - () - getGroupById() - groupId = {} - return = {}", groupId, groupResponseDto);
        return ResponseEntity.ok(groupResponseDto);
    }

    @PutMapping("/{groupId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> updateGroupById(@RequestBody GroupInfoDto groupInfoDto, @PathVariable Long groupId) {
        log.info("GroupController - () - updateGroupById() - groupId = {}", groupId);
        Group group = groupService.getGroupById(groupId);
        if (authorizationService.isLoggedUserAdmin() || authorizationService.isLoggedUserGroupOwner(group.getId())) {
            GroupInfo groupInfo = GroupInfoMapper.mapGroupInfoDtoToGroupInfo(groupInfoDto);
            groupService.updateGroupById(group, groupInfo);

            Map<String, String> body = getBody(String.format("Group with id: %d successfully updated", groupId));

            log.info("GroupController - () - updateGroupById() - groupId = {} updated", groupId);
            return ResponseEntity.ok(body);
        }
        log.error("Unauthorized request Exception occurred!");
        throw new UnauthorizedRequestException("You must be the owner of the group or have administrative rights to update it!");
    }

    @PostMapping("/{groupId}/users/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> joinGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        log.info("GroupController - () - joinGroup() - groupId = {} - userId = {}", groupId, userId);
        Group group = groupService.getGroupById(groupId);
        if (!authorizationService.isLoggedUserIdEqualToBasicUserIdParam(userId)
                || authorizationService.isLoggedUserGroupMember(group)) {
            throw new UnauthorizedRequestException("Access denied");
        }
        groupService.joinGroup(userId, group);
        Map<String, String> body = getBody(
                String.format("User with id %d successfully added the group with id: %d", userId, groupId));
        log.info("GroupController - () - joinGroup() - groupId = {} - userId = {} - group joined by user", groupId, userId);
        return ResponseEntity.ok().body(body);
    }

    @DeleteMapping("/{groupId}/users/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> leaveGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        log.info("GroupController - () - leaveGroup() - groupId = {} - userId = {}", groupId, userId);

        if (!authorizationService.isLoggedUserIdEqualToBasicUserIdParam(userId)) {
            throw new UnauthorizedRequestException("The user id passed does not match logged in user id");
        }
        Group group = groupService.getGroupById(groupId);

        if (!authorizationService.isLoggedUserGroupMember(group)) {
            throw new UnauthorizedRequestException("You must be a member of the group to leave it!");
        }
        groupService.leaveGroup(userId, group);

        Map<String, String> body = getBody(
                String.format("User with id: %d successfully removed from the group with id: %d", userId, groupId));

        log.info("GroupController - () - leaveGroup() - groupId = {} - userId = {} - group left by user", groupId, userId);
        return ResponseEntity.ok().body(body);
    }

    @PatchMapping("/{groupId}/ownership")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> updateGroupOwnershipById(
            @RequestBody OwnershipRequestDto ownershipRequestDto, @PathVariable Long groupId) {
        log.info("GroupController - updateGroupOwnershipById(), - groupId = {}", groupId);
        if (authorizationService.isLoggedUserGroupOwner(groupId) || authorizationService.isLoggedUserAdmin()) {
            Long newOwnerUserId = ownershipRequestDto.getNewOwnerUserId();
            if (authorizationService.checkIfBasicUserExistsById(newOwnerUserId)) {
                groupService.updateGroupOwnershipById(groupId, newOwnerUserId);
            } else {
                log.error("GroupController - updateGroupOwnershipById()", new UnauthorizedRequestException("Unauthorized request"));
                throw new UnauthorizedRequestException("The id of the new group owner does not belong to any basic user account");
            }
        } else {
            log.error("EventController - updateEventOwnershipById()", new UnauthorizedRequestException("Unauthorized request"));
            throw new UnauthorizedRequestException("Logged in user is not authorized to change the group owner of the event with id: " + groupId);
        }
        Map<String, String> body = getBody(String.format("Group with id: %d has a new owner", groupId));

        log.info("GroupController - updateGroupOwnershipById(), - groupId = {} - ownership updated", groupId);
        return ResponseEntity.ok().body(body);
    }

    private Map<String, String> getBody(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        return body;
    }
}