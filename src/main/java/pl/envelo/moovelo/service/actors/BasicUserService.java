package pl.envelo.moovelo.service.actors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.repository.actors.BasicUserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        if (basicUserOptional.isPresent()) {
            return true;
        } else throw new NoSuchElementException("No BasicUser with id: " + userId);
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

}

