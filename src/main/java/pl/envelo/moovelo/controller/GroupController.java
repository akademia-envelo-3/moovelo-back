package pl.envelo.moovelo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.service.AuthorizationService;
import pl.envelo.moovelo.service.group.GroupService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/groups")
@Slf4j
public class GroupController {
    private GroupService groupService;
    private AuthorizationService authorizationService;

    // TODO: 16.02.2023  
//    @PostMapping("")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<GroupResponseDto> createNewGroup(GroupRequestDto groupRequestDto) {
//
//    }
}
