package pl.envelo.moovelo.controller.dto.event.response;

import java.util.Objects;

public class EventIdDto {
    private final long id;

    public EventIdDto(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventIdDto that = (EventIdDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
