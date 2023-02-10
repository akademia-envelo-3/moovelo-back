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
    public static final String ERROR_MESSAGE = "Podales zly ADRES";

    public GeocodingApiDto getGeolocationInfoForAddress(Location locationBeforeRequest) {

        final String address = createAddressFromLocationFields(locationBeforeRequest);

        GeocodingApiDto geocodingApiDto = restTemplate.getForObject(GEOLOCATION_API_BASE_URL + DATA_FORMAT + "?address={address}&key={apiKey}",
                GeocodingApiDto.class, address, API_KEY);

        if (!geocodingApiDto.getResults().isEmpty()) {
            return geocodingApiDto;
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    private String createAddressFromLocationFields(Location location) {
        StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append(location.getStreet())
                .append(ADDRESS_SEPARATOR)
                .append(location.getStreetNumber())
                .append(ADDRESS_SEPARATOR)
                .append(location.getCity());
        return addressBuilder.toString();
    }
}
