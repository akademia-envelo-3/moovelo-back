
package pl.envelo.moovelo.controller.dto.location.geocoding;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "location",
        "location_type",
        "viewport"
})
@Generated("jsonschema2pojo")
public class Geometry {

    @JsonProperty("location")
    private Coordinates coordinates;

    @JsonProperty("location")
    public Coordinates getLocation() {
        return coordinates;
    }

    @JsonProperty("location")
    public void setLocation(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "location=" + coordinates +
                '}';
    }
}
