package pl.envelo.moovelo.controller.dto.location;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LocationDto {
    private Long id;

    private double altitude;

    private double latitude;

    private String postCode;

    private String city;

    private String street;

    private String streetNumber;

    private String apartmentNumber;

    @Override
    public String toString() {
        return "LocationDto{" +
                "altitude=" + altitude +
                ", latitude=" + latitude +
                ", postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                '}';
    }
}
