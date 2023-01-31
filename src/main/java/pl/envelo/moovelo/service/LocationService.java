package pl.envelo.moovelo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.apiClients.GeocodingApiClient;
import pl.envelo.moovelo.controller.dto.location.LocationDto;
import pl.envelo.moovelo.controller.dto.location.geocoding.GeocodingApiDto;
import pl.envelo.moovelo.controller.mapper.GeocodingApiDtoToGeolocationDtoMapper;
import pl.envelo.moovelo.controller.mapper.LocationMapper;
import pl.envelo.moovelo.entity.Location;

@Service
public class LocationService {
    private final GeocodingApiClient geocodingApiClient;

    @Autowired
    public LocationService(GeocodingApiClient geocodingApiClient) {
        this.geocodingApiClient = geocodingApiClient;
    }

    public Location getLocationFromGeocodingApi(Location locationBeforeApiRequest) {
        GeocodingApiDto geocodingApiResponse = sendGeolocationApiRequest(locationBeforeApiRequest);
        LocationDto locationDtoAfterGeolocationApiRequest = GeocodingApiDtoToGeolocationDtoMapper.map(geocodingApiResponse);
        return LocationMapper.mapFromLocationDtoToLocationEntity(null, locationDtoAfterGeolocationApiRequest);
    }

    private GeocodingApiDto sendGeolocationApiRequest(Location locationBeforeApiRequest) {
        return geocodingApiClient.getGeolocationInfoForAddress(locationBeforeApiRequest);
    }

}
