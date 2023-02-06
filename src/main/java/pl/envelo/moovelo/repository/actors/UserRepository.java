package pl.envelo.moovelo.repository.actors;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.envelo.moovelo.entity.actors.User;

import java.util.Optional;

@Primary
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}