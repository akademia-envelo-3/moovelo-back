package pl.envelo.moovelo.controller.dto.location;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.entity.events.EventInfo;

import java.util.List;

@Builder
@Getter
public class LocationDto {
    private long id;

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
