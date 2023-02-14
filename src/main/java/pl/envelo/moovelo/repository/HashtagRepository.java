package pl.envelo.moovelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.Hashtag;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    List<Hashtag> findHashtagsByVisibleIsTrue();

    Hashtag findByHashtagValueIgnoreCase(String hashtagValue);
}