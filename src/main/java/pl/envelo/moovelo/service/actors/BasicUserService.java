package pl.envelo.moovelo.service.actors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.repository.actors.BasicUserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BasicUserService {

    private BasicUserRepository basicUserRepository;

    public List<BasicUser> getAllBasicUsers() {
        return basicUserRepository.findAll();
    }

    public boolean checkIfBasicUserExistsById(Long userId) {
        Optional<BasicUser> basicUserOptional = basicUserRepository.findById(userId);
        if (basicUserOptional.isPresent()) {
            return true;
        } else {
            throw new NoSuchElementException("No BasicUser with id: " + userId);
        }
    }

    public boolean isBasicUserEventOwner(User user, Long eventOwnerUserId) {
        return user.getRole().name().equals("ROLE_USER") &&
                user.getId().equals(eventOwnerUserId);
    }
}

