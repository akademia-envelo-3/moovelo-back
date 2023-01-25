package pl.envelo.moovelo.controller.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.location.LocationDto;
import pl.envelo.moovelo.entity.Location;

@Getter
@AllArgsConstructor
public class LocationMapper {

    public static Location mapToLocationEntity(LocationDto locationDto) {

        Location location = Location.builder()
                .id(locationDto.getId())
                .altitude(locationDto.getAltitude())
                .latitude(locationDto.getLatitude())
                .postcode(locationDto.getPostCode())
                .city(locationDto.getCity())
                .street(locationDto.getStreet())
                .streetNumber(locationDto.getStreetNumber())
                .apartmentNumber(locationDto.getApartmentNumber())
                .build();

        return location;
    }

    public static LocationDto mapToLocationDto(Location location) {

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
