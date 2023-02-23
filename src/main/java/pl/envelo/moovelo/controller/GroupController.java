package pl.envelo.moovelo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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

        Map<String, String> body = new HashMap<>();
        body.put("message", "Successfully removed the group");

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

            Map<String, String> body = new HashMap<>();
            body.put("message", "Group with id: " + groupId + " successfully updated");

            log.info("GroupController - () - updateGroupById() - groupId = {} updated", groupId);
            return ResponseEntity.ok(body);
        }
        log.error("Unauthorized request Exception occurred!");
        throw new UnauthorizedRequestException("You must be the owner of the group or have administrative rights to update it!");
    }

    @PostMapping("/{groupId}/users/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> joinGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        Group group = groupService.getGroupById(groupId);
        if (!authorizationService.isLoggedUserIdEqualToBasicUserIdParam(userId)
                || authorizationService.isLoggedUserGroupMember(group)) {
            throw new UnauthorizedRequestException("Access denied");
        }
        groupService.joinGroup(userId, group);
        Map<String, String> body = new HashMap<>();
        body.put("message", "User with id: " + userId + " successfully added the group with id: " + groupId);
        return ResponseEntity.ok().body(body);
    }
}