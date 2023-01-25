package pl.envelo.moovelo.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.groups.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}