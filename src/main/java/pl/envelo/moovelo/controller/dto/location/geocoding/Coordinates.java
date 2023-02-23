package pl.envelo.moovelo.controller.dto.location.geocoding;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "lat",
        "lng"
})
@Generated("jsonschema2pojo")
public class Coordinates {

    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lng")
    private Double lng;

    @JsonProperty("lat")
    public Double getLat() {
        return lat;
    }

//    @JsonProperty("lat")
//    public void setLat(Double lat) {
//        this.lat = lat;
//    }

    @JsonProperty("lng")
    public Double getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
