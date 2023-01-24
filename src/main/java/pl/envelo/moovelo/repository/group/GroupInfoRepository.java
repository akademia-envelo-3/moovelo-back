package pl.envelo.moovelo.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.groups.GroupInfo;

@Repository
public interface GroupInfoRepository extends JpaRepository<GroupInfo, Long> {
}