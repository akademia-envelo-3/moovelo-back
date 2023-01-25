package pl.envelo.moovelo.repository.actors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.actors.Visitor;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}