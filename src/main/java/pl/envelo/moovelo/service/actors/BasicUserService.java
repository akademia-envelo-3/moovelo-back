package pl.envelo.moovelo.service.actors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.repository.actors.BasicUserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
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
        } else throw new NoSuchElementException("No BasicUser with id: " + userId);
    }

    public BasicUser getBasicUserById(Long id){
        log.info("BasicUserService - getBasicUserById(id = '{}')", id);
        BasicUser basicUser = basicUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("BasicUser with id " + id + " not found."));
        return basicUser;
    }
}
