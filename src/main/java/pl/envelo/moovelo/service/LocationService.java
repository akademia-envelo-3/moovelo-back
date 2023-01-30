package pl.envelo.moovelo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.GeocodingApiClient;
import pl.envelo.moovelo.controller.dto.location.LocationDto;
import pl.envelo.moovelo.controller.dto.location.geocoding.GeocodingApiDto;
import pl.envelo.moovelo.controller.mapper.GeocodingApiDtoToGeolocationDtoMapper;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.repository.LocationRepository;

@Service
public class LocationService {

    private final GeocodingApiClient geocodingApiClient;
    private final LocationRepository locationRepository;

    public LocationService(GeocodingApiClient geocodingApiClient, LocationRepository locationRepository) {
        this.geocodingApiClient = geocodingApiClient;
        this.locationRepository = locationRepository;
    }

    public Location saveLocation(Location location){
       return locationRepository.save(location);
    }

    public LocationDto getLocation(String address) {
        GeocodingApiDto response = geocodingApiClient.getGeolocationInfoForAddress(address);
        return GeocodingApiDtoToGeolocationDtoMapper.map(response);
    }

}
