package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.controller.AuthenticatedUser;
import pl.envelo.moovelo.entity.actors.Role;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.entity.groups.GroupOwner;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.EventOwnerService;
import pl.envelo.moovelo.service.actors.GroupOwnerService;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AuthorizationService {
    AuthenticatedUser authenticatedUser;
    EventOwnerService eventOwnerService;
    GroupOwnerService groupOwnerService;
    BasicUserService basicUserService;


    public boolean isLoggedUserGroupOwner(Long groupId) {
        User loggedInUser = authenticatedUser.getAuthenticatedUser();
        GroupOwner groupOwnerByGroupId = groupOwnerService.getGroupOwnerByGroupId(groupId);
        return basicUserService.isBasicUserOwner(loggedInUser, groupOwnerByGroupId.getUserId());
    }

    public boolean isLoggedUserEventOwner(Long eventId) {
        User loggedInUser = authenticatedUser.getAuthenticatedUser();
        EventOwner eventOwnerByEventId = eventOwnerService.getEventOwnerByEventId(eventId);
        return basicUserService.isBasicUserOwner(loggedInUser, eventOwnerByEventId.getUserId());
    }

    public boolean isLoggedUserAdmin() {
        return authenticatedUser.getAuthenticatedUser().getRole().name().equals("ROLE_ADMIN");
    }

    public Long getLoggedUserId() {
        return authenticatedUser.getAuthenticatedUser().getId();
    }

    public boolean checkIfBasicUserExistsById(Long userId) {
        return basicUserService.checkIfBasicUserExistsById(userId);
    }

    public Long getLoggedBasicUserId() {
        Long loggedUserId = getLoggedUserId();
        if (checkIfBasicUserExistsById(loggedUserId)) {
            return loggedUserId;
        } else {
            throw new NoSuchElementException("Access denied. Logged in user in unauthorized to for the action called!");
        }
    }

    public boolean authorizeGetByOwnerBasicUserId(Long basicUserId) {
        User user = authenticatedUser.getAuthenticatedUser();
        return user.getRole().equals(Role.ROLE_USER) && user.getId().equals(basicUserId);
    }
}
