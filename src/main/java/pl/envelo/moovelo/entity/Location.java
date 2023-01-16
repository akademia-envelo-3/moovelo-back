package pl.envelo.moovelo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.envelo.moovelo.entity.events.EventInfo;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    EventInfo eventInfo;

    Double altitude;

    Double latitude;

    String postcode;

    String city;

    String street;

    String streetNumber;

    String apartmentNumber;
}
