package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.apiClients.GeocodingApiClient;
import pl.envelo.moovelo.controller.dto.location.LocationDto;
import pl.envelo.moovelo.controller.dto.location.geocoding.GeocodingApiDto;
import pl.envelo.moovelo.controller.mapper.GeocodingApiDtoToGeolocationDtoMapper;
import pl.envelo.moovelo.controller.mapper.LocationMapper;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.repository.LocationRepository;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class LocationService {
    private final GeocodingApiClient geocodingApiClient;
    private LocationRepository locationRepository;

    public Location validateLocation(Location location) {

        Location locationAfterGeocodingApiRequest = getLocationAfterGeocodingApiRequest(location);

        Optional<Location> locationComparedWithDb = checkIfLocationExistInDatabase(locationAfterGeocodingApiRequest);

        return locationComparedWithDb.orElseGet(() -> locationRepository.save(locationAfterGeocodingApiRequest));
    }

    private Location getLocationAfterGeocodingApiRequest(Location location) {
        GeocodingApiDto geocodingApiResponse = sendGeolocationApiRequest(location);
        LocationDto locationDtoAfterGeolocationApiRequest = GeocodingApiDtoToGeolocationDtoMapper.map(geocodingApiResponse);
        return LocationMapper.mapFromLocationDtoToLocationEntity(null, locationDtoAfterGeolocationApiRequest);
    }

    private Optional<Location> checkIfLocationExistInDatabase(Location locationBeforeApiRequest) {
        return locationRepository.findLocationByLatitudeAndAltitude(
                locationBeforeApiRequest.getLatitude(),
                locationBeforeApiRequest.getAltitude());
    }

    private GeocodingApiDto sendGeolocationApiRequest(Location locationBeforeApiRequest) {
        return geocodingApiClient.getGeolocationInfoForAddress(locationBeforeApiRequest);
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
