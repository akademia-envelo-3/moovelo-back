package pl.envelo.moovelo.service.actors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.repository.actors.BasicUserRepository;
import pl.envelo.moovelo.service.group.GroupService;

import java.util.*;

@AllArgsConstructor
@Service
@Slf4j
public class BasicUserService {

    private BasicUserRepository basicUserRepository;

    public List<BasicUser> getAllBasicUsers() {
        return basicUserRepository.findAll();
    }

    public boolean checkIfBasicUserExistsById(Long userId) {
        Optional<BasicUser> basicUserOptional = basicUserRepository.findById(userId);
        return basicUserOptional.isPresent();
    }

    public boolean isBasicUserOwner(User user, Long ownerUserId) {
        return user.getRole().name().equals("ROLE_USER") &&
                user.getId().equals(ownerUserId);
    }

    public BasicUser getBasicUserById(Long id) {
        log.info("BasicUserService - getBasicUserById()");
        Optional<BasicUser> basicUserOptional = basicUserRepository.findById(id);
        if (basicUserOptional.isEmpty()) {
            throw new NoSuchElementException("No basicUser with id: " + id);
        }
        log.info("BasicUserService - getBasicUserById() return {}", basicUserOptional.get());
        return basicUserOptional.get();
    }

    public Set<Group> getAllBasicUserGroups(Long basicUserId) {
        log.info("BasicUserService - getAllBasicUserGroups()");
        BasicUser basicUserById = getBasicUserById(basicUserId);
        Set<Group> basicUserGroups = basicUserById.getGroups();
        log.info("BasicUserService - getAllBasicUserGroups() - return basicUserGroups = {}", basicUserGroups);
        return basicUserGroups;
    }

    public BasicUser updateBasicUser(BasicUser basicUser) {
        return basicUserRepository.save(basicUser);
    }
}

