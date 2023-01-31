package pl.envelo.moovelo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.GeocodingApiClient;
import pl.envelo.moovelo.controller.dto.location.LocationDto;
import pl.envelo.moovelo.controller.dto.location.geocoding.GeocodingApiDto;
import pl.envelo.moovelo.controller.mapper.GeocodingApiDtoToGeolocationDtoMapper;

@Service
public class LocationService {

    private final GeocodingApiClient geocodingApiClient;

    @Autowired
    public LocationService(GeocodingApiClient geocodingApiClient) {
        this.geocodingApiClient = geocodingApiClient;
    }

    public LocationDto getLocation(String address) {
        GeocodingApiDto response = geocodingApiClient.getGeolocationInfoForAddress(address);
        return GeocodingApiDtoToGeolocationDtoMapper.map(response);
    }

}
