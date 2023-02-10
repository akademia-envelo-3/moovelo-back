package pl.envelo.moovelo.service.actors;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.repository.actors.BasicUserRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BasicUserService {

    private BasicUserRepository basicUserRepository;

    public List<BasicUser> getAllBasicUsers() {
        return basicUserRepository.findAll();
    }

    public BasicUser getBasicUserById(Long id){
        BasicUser basicUser = basicUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("BasicUser with id " + id + " not found."));
        return basicUser;
    }


}
