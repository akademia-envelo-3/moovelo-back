package pl.envelo.moovelo.LocationTests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.envelo.moovelo.controller.dto.location.LocationDto;
import pl.envelo.moovelo.controller.mapper.LocationMapper;
import pl.envelo.moovelo.entity.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LocationTests {

    @Test
    void mapppingFromLocationDtoToLocationEntityTest() {
        //given

        LocationDto locationDto = LocationDto.builder()
                .altitude(20.32)
                .latitude(43.11)
                .postCode("02-765")
                .city("Warsaw")
                .street("Cybernetyki")
                .streetNumber("17C")
                .apartmentNumber("23")
                .build();

        Location location = new Location();
        location.setAltitude(20.32);
        location.setLatitude(43.11);
        location.setPostcode("02-765");
        location.setCity("Warsaw");
        location.setStreet("Cybernetyki");
        location.setStreetNumber("17C");
        location.setApartmentNumber("23");

        //then



    }


    @Test
    void mapppingFromLocationEntityToLocationDtoTest() {
        //given

        LocationDto locationDto = LocationDto.builder()
                .id(2)
                .altitude(20.32)
                .latitude(43.11)
                .postCode("02-765")
                .city("Warsaw")
                .street("Cybernetyki")
                .streetNumber("17C")
                .apartmentNumber("23")
                .build();

        Location location = new Location();
        location.setId(2L);
        location.setAltitude(20.32);
        location.setLatitude(43.11);
        location.setPostcode("02-765");
        location.setCity("Warsaw");
        location.setStreet("Cybernetyki");
        location.setStreetNumber("17C");
        location.setApartmentNumber("23");

        //then

        assertEquals(locationDto, LocationMapper.mapFromLocationEntityToLocationDto(location));
    }
} //TODO poprawić testy dla Location i zrobić dla testy dla Geocoding
