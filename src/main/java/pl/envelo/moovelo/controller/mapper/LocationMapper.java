package pl.envelo.moovelo.controller.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.location.LocationDto;
import pl.envelo.moovelo.entity.Location;

@Getter
@AllArgsConstructor
public class LocationMapper {

    public static Location mapFromLocationDtoToLocationEntity(Long id, LocationDto locationDto) {

        Location location = new Location();
        location.setId(id);
        location.setAltitude(locationDto.getAltitude());
        location.setLatitude(locationDto.getLatitude());
        location.setPostcode(locationDto.getPostCode());
        location.setCity(locationDto.getCity());
        location.setStreet(locationDto.getStreet());
        location.setStreetNumber(locationDto.getStreetNumber());
        location.setApartmentNumber(locationDto.getApartmentNumber());

        return location;
    }

    public static LocationDto mapFromLocationEntityToLocationDto(Location location) {

        LocationDto locationDto = LocationDto.builder()
                .id(location.getId())
                .altitude(location.getAltitude())
                .latitude(location.getLatitude())
                .postCode(location.getPostcode())
                .city(location.getCity())
                .street(location.getStreet())
                .streetNumber(location.getStreetNumber())
                .apartmentNumber(location.getApartmentNumber())
                .build();

        return locationDto;
    }

}
