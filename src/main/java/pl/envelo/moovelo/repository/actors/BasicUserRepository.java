package pl.envelo.moovelo.repository.actors;

import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.User;

import java.util.List;

@Repository
public interface BasicUserRepository extends UserRepository {

    List<BasicUser> findAll();
}