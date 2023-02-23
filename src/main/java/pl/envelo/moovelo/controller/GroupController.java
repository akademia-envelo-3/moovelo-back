package pl.envelo.moovelo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.dto.group.GroupRequestDto;
import pl.envelo.moovelo.controller.dto.group.GroupResponseDto;
import pl.envelo.moovelo.controller.mapper.group.GroupMapper;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.service.AuthorizationService;
import pl.envelo.moovelo.service.group.GroupService;

import java.net.URI;

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

    @GetMapping("/{groupId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<GroupResponseDto> getGroupById(@PathVariable Long groupId) {
        log.info("GroupController - () - getGroupById() - groupId = {}", groupId);
        Group group = groupService.getGroupById(groupId);
        GroupResponseDto groupResponseDto = GroupMapper.mapGroupToGroupResponseDto(group);
        log.info("GroupController - () - getGroupById() - groupId = {} - return = {}", groupId, groupResponseDto);
        return ResponseEntity.ok(groupResponseDto);
    }
}
