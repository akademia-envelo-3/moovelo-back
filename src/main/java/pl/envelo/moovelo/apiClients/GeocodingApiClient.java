package pl.envelo.moovelo.apiClients;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.envelo.moovelo.controller.dto.location.geocoding.GeocodingApiDto;
import pl.envelo.moovelo.entity.Location;

@Component
public class GeocodingApiClient {

    public static final RestTemplate restTemplate = new RestTemplate();
    private static final String GEOLOCATION_API_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/";
    private static final String DATA_FORMAT = "json";
    private static final String API_KEY = "AIzaSyDV6J_lwZ8KtNQg_1DFdJLKQRPjrlxCm4E";
    public static final char ADDRESS_SEPARATOR = '%';

    public GeocodingApiDto getGeolocationInfoForAddress(Location locationBeforeRequest) {

        final String address = createAddressFromLocationFields(locationBeforeRequest);

        return restTemplate.getForObject(GEOLOCATION_API_BASE_URL + DATA_FORMAT + "?address={address}&key={apiKey}",
                GeocodingApiDto.class, address, API_KEY);
    }

    private String createAddressFromLocationFields(Location location) {
        String addressBuilder = location.getStreet() +
                ADDRESS_SEPARATOR +
                location.getStreetNumber() +
                ADDRESS_SEPARATOR +
                location.getCity();
        return addressBuilder;
    }
}
