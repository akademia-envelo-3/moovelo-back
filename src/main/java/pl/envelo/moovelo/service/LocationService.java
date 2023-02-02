package pl.envelo.moovelo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.repository.LocationRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Method check if location is assigned to any EventInfo. If list of EventInfos is empty,
     * then location entity is remove from database.
     */
    public void removeLocationWithNoEvents(Location location) {
        log.info("LocationService - removeLocationWithNoEvents() - location = {}", location);
        if (location.getEventsInfos().isEmpty()) {
            locationRepository.delete(location);
            log.info("LocationService - removeLocationWithNoEvents() - location removed");
        }
    }
}
