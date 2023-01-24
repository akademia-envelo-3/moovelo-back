
package pl.envelo.moovelo.controller.dto.location.geocoding;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "results",
    "status"
})
@Generated("jsonschema2pojo")
public class GeocodingApiDto {

    @JsonProperty("results")
    private List<Result> results;


    @JsonProperty("results")
    public List<Result> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GeocodingApiDto{" +
                "results=" + results +
                '}';
    }
}
