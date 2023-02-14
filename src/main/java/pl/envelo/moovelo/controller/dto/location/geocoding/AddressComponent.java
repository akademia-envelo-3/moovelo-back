package pl.envelo.moovelo.controller.dto.location.geocoding;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "long_name",
    "short_name",
    "types"
})
@Generated("jsonschema2pojo")
public class AddressComponent {

    @JsonProperty("long_name")
    private String longName;
    @JsonProperty("types")
    private List<String> types;

    @JsonProperty("long_name")
    public String getLongName() {
        return longName;
    }

    @JsonProperty("long_name")
    public void setLongName(String longName) {
        this.longName = longName;
    }

    @JsonProperty("types")
    public List<String> getTypes() {
        return types;
    }

    @JsonProperty("types")
    public void setTypes(List<String> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "AddressComponent{" +
                "longName='" + longName + '\'' +
                ", types=" + types +
                '}';
    }
}
