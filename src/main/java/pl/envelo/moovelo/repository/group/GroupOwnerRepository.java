package pl.envelo.moovelo.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.envelo.moovelo.entity.groups.GroupOwner;

import java.util.Optional;

public interface GroupOwnerRepository extends JpaRepository<GroupOwner, Long> {
    Optional<GroupOwner> findByGroupsId(Long groupId);
}
