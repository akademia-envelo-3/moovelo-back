package pl.envelo.moovelo.repository.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.groups.Group;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupInfoName(String name);

    @Query("SELECT g FROM Group g WHERE :user MEMBER OF g.members")
    Page<Group> findAllGroupsWhereUserIsMember(@Param("user") BasicUser basicUser, Pageable pageable);

    @Query("SELECT g FROM Group g WHERE :user NOT MEMBER OF g.members")
    Page<Group> findAllGroupsWhereUserIsNotMember(@Param("user") BasicUser user, Pageable pageable);

    Page<Group> findAllByGroupOwnerUserId(Long ownerUserId, Pageable pageable);
}