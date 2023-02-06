package pl.envelo.moovelo.repository.actors;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.envelo.moovelo.entity.actors.User;

@Primary
public interface UserRepository<I extends User> extends JpaRepository<User, Long> {
}