package pl.envelo.moovelo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.dto.group.GroupListResponseDto;
import pl.envelo.moovelo.controller.dto.group.GroupRequestDto;
import pl.envelo.moovelo.controller.dto.group.GroupResponseDto;
import pl.envelo.moovelo.controller.mapper.group.GroupMapper;
import pl.envelo.moovelo.controller.searchutils.GroupPage;
import pl.envelo.moovelo.entity.groups.Group;
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
    public ResponseEntity<Page<GroupListResponseDto>> getAllGroups(Boolean membership, GroupPage groupPage) {
        log.info("GroupController - getAllGroups()");
        Page<Group> groups;
        if (authorizationService.isLoggedUserBasicUser() && membership != null) {
            groups = groupService.getAllGroupsForBasicUser(authorizationService.getLoggedBasicUserId(), membership, groupPage);
        } else {
            groups = groupService.getAllGroupsWithoutFiltering(groupPage);
        }
        Page<GroupListResponseDto> groupListResponseDtoPage =
                groups.map(group -> GroupMapper.mapGroupToGroupListResponseDto(group, authorizationService.isLoggedUserGroupMember(group)));
        log.info("GroupController - getAllGroups() - return groupListResponseDtoPage = {}", groupListResponseDtoPage);
        return new ResponseEntity<>(groupListResponseDtoPage, HttpStatus.OK);
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

    private Map<String, String> getBody(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        return body;
    }
}