package pl.envelo.moovelo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.events.EventInfo;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "location")
    private List<EventInfo> eventsInfos;

    private Double altitude;

    private Double latitude;

    private String postcode;

    private String city;

    private String street;

    private String streetNumber;

    private String apartmentNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return Objects.equals(altitude, location.altitude)
                && Objects.equals(latitude, location.latitude)
                && Objects.equals(apartmentNumber, location.apartmentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(altitude, latitude, apartmentNumber);
    }
}
