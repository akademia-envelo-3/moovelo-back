package pl.envelo.moovelo.controller.dto.location.geocoding;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationDto {

    private Double altitude;
    private Double latitude;
    private String street;
    private Long streetNumber;
    private String postcode;
    private String city;

}
