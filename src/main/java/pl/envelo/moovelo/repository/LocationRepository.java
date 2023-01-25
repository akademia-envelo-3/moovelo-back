package pl.envelo.moovelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}