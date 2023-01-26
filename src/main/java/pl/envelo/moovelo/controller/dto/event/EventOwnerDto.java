package pl.envelo.moovelo.controller.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
public class EventOwnerDto {
    private long id;
    @JsonProperty("newOwnerUserId")
    private long userId;
    private String firstname;
    private String lastname;
    private List<EventIdDto> events;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventOwnerDto that = (EventOwnerDto) o;
        return id == that.id && userId == that.userId && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(events, that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, firstname, lastname, events);
    }
}
