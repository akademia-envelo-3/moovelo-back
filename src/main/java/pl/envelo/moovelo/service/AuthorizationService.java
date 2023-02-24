package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.controller.AuthenticatedUser;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.Role;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.groups.GroupOwner;
import pl.envelo.moovelo.exception.UnauthorizedRequestException;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.EventOwnerService;
import pl.envelo.moovelo.service.actors.GroupOwnerService;
import pl.envelo.moovelo.service.event.EventService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class AuthorizationService {
    AuthenticatedUser authenticatedUser;
    EventOwnerService eventOwnerService;
    GroupOwnerService groupOwnerService;
    BasicUserService basicUserService;
    EventService eventService;

    // TODO: 17.02.2023 - metoda potrzebna do mapowania Grupy na GroupListResponseDto (pole isUserMember)
    public boolean isLoggedUserGroupMember(Group group) {
        log.info("AuthorizationService - isLoggedUserGroupMember() - group = {}", group);
        User user = authenticatedUser.getAuthenticatedUser();
        List<Long> groupMembersUserIds = group.getMembers().stream().map(BasicUser::getId).toList();
        boolean isUserGroupMember = groupMembersUserIds.contains(user.getId());
        log.info("AuthorizationService - isLoggedUserGroupMember() - group = {} - return isUserGroupMember = {}", group, isUserGroupMember);
        return isUserGroupMember;
    }

    public boolean isLoggedUserGroupOwner(Long groupId) {
        log.info("AuthorizationService - isLoggedUserGroupOwner() - groupId = {}", groupId);
        User loggedInUser = authenticatedUser.getAuthenticatedUser();
        GroupOwner groupOwnerByGroupId = groupOwnerService.getGroupOwnerByGroupId(groupId);
        boolean isUserGroupOwner = basicUserService.isBasicUserOwner(loggedInUser, groupOwnerByGroupId.getUserId());
        log.info("AuthorizationService - isLoggedUserGroupOwner() - groupId = {} - return isUserGroupOwner = {}", groupId, isUserGroupOwner);
        return isUserGroupOwner;
    }

    public boolean isLoggedUserEventOwner(Long eventId) {
        log.info("AuthorizationService - isLoggedUserEventOwner() - eventId = {}", eventId);
        User loggedInUser = authenticatedUser.getAuthenticatedUser();
        EventOwner eventOwnerByEventId = eventOwnerService.getEventOwnerByEventId(eventId);
        boolean isUserEventOwner = basicUserService.isBasicUserOwner(loggedInUser, eventOwnerByEventId.getUserId());
        log.info("AuthorizationService - isLoggedUserEventOwner() - eventId = {} - return isUserEventOwner = {}", eventId, isUserEventOwner);
        return isUserEventOwner;
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
        log.info("AuthorizationService - getLoggedBasicUserId()");
        Long loggedUserId = getLoggedUserId();
        if (checkIfBasicUserExistsById(loggedUserId)) {
            log.info("AuthorizationService - getLoggedBasicUserId() - return loggedUserId = {}", loggedUserId);
            return loggedUserId;
        } else {
            throw new NoSuchElementException("Access denied. Logged in user in unauthorized to for the action called!");
        }
    }

    public User getLoggedUser() {
        log.info("AuthorizationService - getLoggedUser()");
        User loggedUser = authenticatedUser.getAuthenticatedUser();

        log.info("AuthorizationService - getLoggedUser() - return loggedUser = {}", loggedUser);
        return loggedUser;
    }

    public boolean authorizeGetByOwnerBasicUserId(Long basicUserId) {
        log.info("AuthorizationService - authorizeGetByOwnerBasicUserId() - basicUserId = {}", basicUserId);
        User user = authenticatedUser.getAuthenticatedUser();
        boolean isLoggedUserIdEqualToBasicUserId = user.getRole().equals(Role.ROLE_USER) && user.getId().equals(basicUserId);
        log.info("AuthorizationService - authorizeGetByOwnerBasicUserId() " +
                "- basicUserId = {} - return isLoggedUserIdEqualToBasicUserId = {}", basicUserId, isLoggedUserIdEqualToBasicUserId);
        return isLoggedUserIdEqualToBasicUserId;
    }

    public void checkIfLoggedUserHasAccessToEvent(Long eventId, EventType eventType) {
        log.info("AuthorizationService - checkIfLoggedUserHasAccessToEvent()");
        User user = getLoggedUser();
        Event event = eventService.getEventById(eventId, eventType);

        if (user.getRole().equals(Role.ROLE_USER) &&
                !event.getUsersWithAccess().contains((BasicUser) user)) {
            throw new UnauthorizedRequestException("User with id " + user.getId() + " does not have an access to event with id " + event.getId());
        }
    }
}
