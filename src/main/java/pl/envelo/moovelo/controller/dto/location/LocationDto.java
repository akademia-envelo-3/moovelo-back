package pl.envelo.moovelo.controller.dto.location;

import lombok.Builder;

@Builder
public class LocationDto {
    private long id;
    private double altitude;

    private double latitude;

    private String postCode;

    private String city;

    private String street;

    private String streetNumber;

    private String apartmentNumber;
}
