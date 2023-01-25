package pl.envelo.moovelo.LocationTests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.envelo.moovelo.controller.dto.location.LocationDto;
import pl.envelo.moovelo.controller.mapper.LocationMapper;
import pl.envelo.moovelo.entity.Location;

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

        Location location = Location.builder()
                .altitude(20.32)
                .latitude(43.11)
                .postcode("02-765")
                .city("Warsaw")
                .street("Cybernetyki")
                .streetNumber("17C")
                .apartmentNumber("23")
                .build();

        //when


        //then


        assertTrue(location.equals(LocationMapper.mapFromLocationDtoToLocationEntity(locationDto)));


    }
}
