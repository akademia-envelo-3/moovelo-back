package pl.envelo.moovelo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.envelo.moovelo.entity.events.EventInfo;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
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
}
