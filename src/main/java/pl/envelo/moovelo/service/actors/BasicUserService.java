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
        return basicUserOptional.isPresent();
    }

    public boolean isBasicUserOwner(User user, Long ownerUserId) {
        return user.getRole().name().equals("ROLE_USER") &&
                user.getId().equals(ownerUserId);
    }
}

